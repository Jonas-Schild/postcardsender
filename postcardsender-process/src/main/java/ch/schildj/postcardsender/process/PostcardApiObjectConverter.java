package ch.schildj.postcardsender.process;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;
import ch.schildj.postcardsender.apicall.model.RequestLogDto;
import ch.schildj.postcardsender.apicall.model.apiResponseDto.CampaignStatisticResponse;
import ch.schildj.postcardsender.apicall.model.apiResponseDto.CodeMessage;
import ch.schildj.postcardsender.domain.enums.MessageType;
import ch.schildj.postcardsender.domain.model.Campaign;
import ch.schildj.postcardsender.domain.model.Cardhistory;
import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.model.RespMessage;
import ch.schildj.postcardsender.domain.model.dto.CampaignStatisticDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class is to convert objects from the process component to objects from the apicall component or the other way
 */

public class PostcardApiObjectConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcardApiObjectConverter.class);

    /*
     * converts a postcard entity to a PostcardApiDto
     *
     * @param postcard       - the postcard
     * @return PostcardApiDto
     */
    public static PostcardApiDto toPostcardApiDto(Postcard postcard) {

        PostcardApiDto postcardApiDto = new PostcardApiDto();

        postcardApiDto.setId(postcard.getId());
        postcardApiDto.setKey(postcard.getKey());
        postcardApiDto.setText(postcard.getText());
        postcardApiDto.setSenderFirstname(postcard.getSenderFirstname());
        postcardApiDto.setSenderLastname(postcard.getSenderLastname());
        postcardApiDto.setSenderStreet(postcard.getSenderStreet());
        postcardApiDto.setSenderHousenr(postcard.getSenderHousenr());
        postcardApiDto.setSenderZip(postcard.getSenderZip());
        postcardApiDto.setSenderCity(postcard.getSenderCity());
        postcardApiDto.setRecipientFirstname(postcard.getRecipientFirstname());
        postcardApiDto.setRecipientLastname(postcard.getRecipientLastname());
        postcardApiDto.setRecipientStreet(postcard.getRecipientStreet());
        postcardApiDto.setRecipientHousenr(postcard.getRecipientHousenr());
        postcardApiDto.setRecipientZip(postcard.getRecipientZip());
        postcardApiDto.setRecipientCity(postcard.getRecipientCity());

        Campaign c = postcard.getCampaign();

        postcardApiDto.setCampaignId(c.getId());
        postcardApiDto.setCampKey(c.getKey());

        switch (c.getBrandType()) {
            case TEXT:
                postcardApiDto.setBrandText(c.getBrandText());
                postcardApiDto.setBrandTextColor(c.getBrandTextColor());
                postcardApiDto.setBrandBlockColor(c.getBrandBlockColor());
                postcardApiDto.setBrandQr(null);
                postcardApiDto.setBrandImage(null);
                break;
            case IMAGE:
                postcardApiDto.setBrandText(null);
                postcardApiDto.setBrandQr(null);
                if (c.getBrandingImg() != null) {
                    Blob brandImg = c.getBrandingImg().getFile();
                    try {
                        postcardApiDto.setBrandImage(brandImg.getBytes(1, (int) brandImg.length()));
                    } catch (SQLException e) {
                        LOGGER.error(e.toString());
                    }
                    ;
                }
                break;
            case QR:
                postcardApiDto.setBrandText(c.getBrandText());
                postcardApiDto.setBrandTextColor(c.getBrandTextColor());
                postcardApiDto.setBrandBlockColor(c.getBrandBlockColor());
                postcardApiDto.setBrandQr(c.getBrandQr());
                postcardApiDto.setBrandImage(null);
                break;
            default:
                postcardApiDto.setBrandText(null);
                postcardApiDto.setBrandQr(null);
                postcardApiDto.setBrandImage(null);
                break;
        }

        if (c.getStamp() != null) {
            Blob stamp = c.getStamp().getFile();
            try {
                postcardApiDto.setStampImage(stamp.getBytes(1, (int) stamp.length()));
            } catch (SQLException e) {
                LOGGER.error(e.toString());
            }
            ;
        }

        if (postcard.getFrontimage() != null) {
            Blob image = postcard.getFrontimage().getFile();
            try {
                postcardApiDto.setFrontImage(image.getBytes(1, (int) image.length()));
            } catch (SQLException e) {
                LOGGER.error(e.toString());
            }
            ;
        }

        return postcardApiDto;

    }

    /*
     * converts a request-logs to history-entities
     *
     * @param requestLogDtos   - list of logs
     * @param postcard         - the postcard
     * @return list of history-entities
     */
    public static ArrayList<Cardhistory> toCardHistory(ArrayList<RequestLogDto> requestLogDtos, Postcard postcard) {

        ArrayList<Cardhistory> cardHist = new ArrayList<>();

        if (requestLogDtos.size() > 0) {
            for (RequestLogDto log : requestLogDtos) {
                Cardhistory ch = new Cardhistory();
                ch.setPostcard(postcard);
                ch.setRespHttpError(log.getRespHttpError());
                ch.setRespHttpCode(log.getRespHttpCode());
                ch.setRequest(log.getRequest());
                ch.setLastModification(log.getCreation());

                List<RespMessage> respMessages = new ArrayList<>();

                if (log.getWarnings() != null && log.getWarnings().size() > 0) {
                    for (CodeMessage resp : log.getErrors()) {
                        respMessages.add(new RespMessage(ch, MessageType.WARNING, resp.getCode(), resp.getDescription()));
                    }
                }

                if (log.getErrors() != null && log.getErrors().size() > 0) {
                    for (CodeMessage resp : log.getErrors()) {
                        respMessages.add(new RespMessage(ch, MessageType.ERROR, resp.getCode(), resp.getDescription()));
                    }
                }

                ch.setRespMessageList(respMessages);

                cardHist.add(ch);

            }
        }

        return cardHist;


    }

    /*
     * converts statistic infos to CampaignStatisticDTO
     *
     * @param campaignStatisticResponse   - statistic infos
     * @return CampaignStatisticDTO
     */
    public static CampaignStatisticDTO toCampaignStatisticDTO(CampaignStatisticResponse campaignStatisticResponse) {
        return new CampaignStatisticDTO(campaignStatisticResponse.getCampaignKey(), campaignStatisticResponse.getQuota(), campaignStatisticResponse.getSendPostcards(), campaignStatisticResponse.getFreeToSendPostcards());
    }


}
