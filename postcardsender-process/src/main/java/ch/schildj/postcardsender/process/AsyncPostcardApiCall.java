package ch.schildj.postcardsender.process;

import ch.schildj.postcardsender.apicall.model.RequestLogDto;
import ch.schildj.postcardsender.apicall.process.PostCardApiCall;
import ch.schildj.postcardsender.apicall.process.PostcardApiCallProvider;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import ch.schildj.postcardsender.domain.model.Cardhistory;
import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.model.RespMessage;
import ch.schildj.postcardsender.domain.repository.CardhistoryRepository;
import ch.schildj.postcardsender.domain.repository.PostcardRepository;
import ch.schildj.postcardsender.domain.repository.RespMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;

/*
 * This class communicates asyncronous with the Postcard API
 */
@Component
public class AsyncPostcardApiCall {


    @Autowired
    private CardhistoryRepository cardhistoryRepository;

    @Autowired
    private RespMessageRepository respMessageRepository;

    @Autowired
    private PostcardRepository postcardRepository;

    @Autowired
    private PostcardApiCallProvider postcardApiCallProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPostcardApiCall.class);

    /*
     * make a new api-call with a defined type of action
     *
     * @param postcard      - the postcard to be transmitted
     * @param type          - part to be updated
     */

    @Async("threadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void apiCallWithCardElements(Postcard postcard, PostcardManager.CardElementType type) {

        if (type != PostcardManager.CardElementType.NEW) {
            // wait a little bit, to assure that the prior creation-request is finished
            // Could be improved by implementing a message-queue with the requests
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
        ArrayList<RequestLogDto> requestLogDtos = new ArrayList<>();
        try {
            // reload Postcard
            postcardRepository.flush();
            // postcardRepository.refresh(postcard);
            postcard = postcardRepository.getOne(postcard.getId());
            PostCardApiCall call = postcardApiCallProvider.getNewPostCardApiCall(PostcardApiObjectConverter.toPostcardApiDto(postcard));
            if (type != PostcardManager.CardElementType.NEW) {
                if (postcard.getKey() == null) {
                    throw new Exception("Card can't be updated, when not created");
                }
                // check if card isn't allready APPROVED or doesn't exists (NOT_FOUND)
                // if yes, create a new Card
                String state = call.getState();
                if (state.compareTo("APPROVED") == 0 || state.compareTo("NOT_FOUND") == 0) {
                    type = PostcardManager.CardElementType.NEW;
                }
            }

            switch (type) {
                case NEW:
                    requestLogDtos = call.createCard();
                    // save cardkey
                    postcard.setKey(requestLogDtos.get(0).getPostcardKey());
                    break;
                case TEXT:
                    requestLogDtos = call.updateText();
                    break;
                case SENDER:
                    requestLogDtos = call.updateSender();
                    break;
                case RECIPIENT:
                    requestLogDtos = call.updateRecipient();
                    break;
                case APPROVE:
                    requestLogDtos = call.approve();
                    break;
                case IMAGE:
                    requestLogDtos = call.updateFrontImage();
                    break;
                case BRAND_IMG:
                    requestLogDtos = call.updateBrandingImage();
                    break;
                case BRAND_QR:
                    requestLogDtos = call.updateBrandingQr();
                    break;
                case BRAND_TEXT:
                    requestLogDtos = call.updateBrandingText();
                    break;
                default:
                    break;
            }
            // save history
            persistCardHistory(requestLogDtos, postcard);
            TransmissionState transmissionState = getTransmissionState(requestLogDtos);
            postcard.setTransmissionState(transmissionState);
            this.postcardRepository.saveAndFlush(postcard);

        } catch (Exception e) {
            if (requestLogDtos.size() > 0) {
                // save history
                persistCardHistory(requestLogDtos, postcard);
            } else if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httperr = (HttpStatusCodeException) e;
                RequestLogDto requestLog = new RequestLogDto();
                requestLog.setRequest(type.name());
                requestLog.setPostcardId(postcard.getId());
                requestLog.setPostcardKey(postcard.getKey());
                requestLogDtos = new ArrayList<>();
                requestLogDtos.add(requestLog);
                requestLog.setRespHttpCode(httperr.getStatusCode().value());
                requestLog.setRespHttpError("Failed with " + httperr.getStatusCode().getReasonPhrase());
                // save history
                persistCardHistory(requestLogDtos, postcard);
            }

            postcard.setTransmissionState(TransmissionState.ERROR);
            this.postcardRepository.saveAndFlush(postcard);
        }
    }

    /*
     * save communication to a postcard
     *
     * @param postcard       - the postcard
     * @param requestLogDtos - the logs
     */
    private void persistCardHistory(ArrayList<RequestLogDto> requestLogDtos, Postcard postcard) {
        for (Cardhistory cardhistory : PostcardApiObjectConverter.toCardHistory(requestLogDtos, postcard)) {
            if (cardhistory.getRespMessageList().size() > 0) {
                List<RespMessage> respMessages = cardhistory.getRespMessageList();
                for (RespMessage respMessage : respMessages) {
                    respMessageRepository.save(respMessage);
                }
            }
            cardhistoryRepository.save(cardhistory);
            List<Cardhistory> ch = postcard.getCardhistoryList();
            ch.add(cardhistory);
            // postcard.setCardhistoryList(ch);
        }
    }

    /*
     * evaluate if communication was successful
     *
     * @param requestLogDtos - the logs
     * @return the state of the transmission
     */
    private TransmissionState getTransmissionState(ArrayList<RequestLogDto> requestLogDtos) {

        try {
            RequestLogDto requestLogDto = requestLogDtos.get(requestLogDtos.size() - 1);

            if (requestLogDto.getErrors() != null && requestLogDto.getErrors().size() > 0) {
                return TransmissionState.ERROR;
            }

            if (requestLogDto.getWarnings() != null && requestLogDto.getWarnings().size() > 0) {
                return TransmissionState.WARNING;
            }

            return TransmissionState.OK;
        } catch (Exception e) {
            return TransmissionState.ERROR;
        }
    }

}
