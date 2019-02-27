package ch.schildj.postcardsender.process;

import ch.schildj.postcardsender.apicall.process.PostCardApiCall;
import ch.schildj.postcardsender.apicall.process.PostcardApiCallProvider;
import ch.schildj.postcardsender.domain.enums.PostcardState;
import ch.schildj.postcardsender.domain.enums.TransmissionState;
import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.model.Postcard;
import ch.schildj.postcardsender.domain.model.dto.AddressDTO;
import ch.schildj.postcardsender.domain.model.dto.CardSearchDTO;
import ch.schildj.postcardsender.domain.model.dto.PostcardDTO;
import ch.schildj.postcardsender.domain.repository.CampaignRepository;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import ch.schildj.postcardsender.domain.repository.PostcardRepository;
import ch.schildj.postcardsender.domain.specification.PostcardSpec;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.List;

/**
 * Class PostcardManager.
 * Manage postcards
 */
@Component
public class PostcardManager {

    @Autowired
    private PostcardRepository postcardRepository;

    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostcardApiCallProvider postcardApiCallProvider;

    @Autowired
    private AsyncPostcardApiCall asyncPostcardApiCall;

    public enum CardElementType {
        NEW, TEXT, SENDER, RECIPIENT, APPROVE, IMAGE, BRAND_TEXT, BRAND_QR, BRAND_IMG
    }

    /*
     * converts a postcard-DTO to an entity
     *
     * @param postcardDTO       - the transfer object
     * @return the entity
     */
    public Postcard toEntity(PostcardDTO postcardDTO) {
        Postcard p;
        if (postcardDTO.getId() == null) {
            p = new Postcard();
            p.setTransmissionState(TransmissionState.OPEN);
            p.setStatus(PostcardState.OPEN);
        } else {
            p = postcardRepository.getOne(postcardDTO.getId());
        }

        p.setText(postcardDTO.getText());
        p.setCampaign(campaignRepository.getOne(postcardDTO.getCampaignId()));
        p.setSenderFirstname(postcardDTO.getSenderFirstname());
        p.setSenderLastname(postcardDTO.getSenderLastname());
        p.setSenderStreet(postcardDTO.getSenderStreet());
        p.setSenderHousenr(postcardDTO.getSenderHousenr());
        p.setSenderZip(postcardDTO.getSenderZip());
        p.setSenderCity(postcardDTO.getSenderCity());
        p.setRecipientFirstname(postcardDTO.getRecipientFirstname());
        p.setRecipientLastname(postcardDTO.getRecipientLastname());
        p.setRecipientStreet(postcardDTO.getRecipientStreet());
        p.setRecipientHousenr(postcardDTO.getRecipientHousenr());
        p.setRecipientZip(postcardDTO.getRecipientZip());
        p.setRecipientCity(postcardDTO.getRecipientCity());

        if (postcardDTO.getFrontImageId() != null) {
            p.setFrontimage(imageRepository.getOne(postcardDTO.getFrontImageId()));
        }

        return p;
    }

    /*
     * saves a new postcard
     *
     * @param postcardDTO       - the postcard
     * @param ipAddressCaller   - the ip address of the creator
     * @return the id of the created postcard
     */
    @Transactional
    public Long createNewPostcard(PostcardDTO postcardDTO, String ipAddressCaller) {
        Postcard newPostcard = this.toEntity(postcardDTO);
        newPostcard.setIpAddress(ipAddressCaller);
        newPostcard.setTransmissionState(TransmissionState.OPEN);
        postcardRepository.saveAndFlush(newPostcard);
        return this.createNewPostcard(newPostcard);
    }

    /*
     * transmit a new postcard
     *
     * @param postcardDTO       - the postcard
     * @return the id of the created postcard
     */
    private Long createNewPostcard(Postcard p) {
        asyncPostcardApiCall.apiCallWithCardElements(p, CardElementType.NEW);
        return p.getId();
    }

    /*
     * updates the sendertext
     *
     * @param id       - the postcard-id
     * @param id       - the new text
     */
    @Transactional
    public void updateText(Long id, String text) {
        Postcard updPostcard = postcardRepository.getOne(id);

        // exit when not allowed
        if (!isUpdateAllowed(updPostcard)) return;

        updPostcard.setText(text);
        updPostcard.setTransmissionState(TransmissionState.OPEN);
        postcardRepository.saveAndFlush(updPostcard);
        asyncPostcardApiCall.apiCallWithCardElements(updPostcard, CardElementType.TEXT);
    }

    /*
     * approve the postcard
     *
     * @param id       - the postcard-id
     */
    @Transactional
    public void approve(Long id) {
        Postcard updPostcard = postcardRepository.getOne(id);

        // exit when not allowed
        if (!isUpdateAllowed(updPostcard)) return;

        updPostcard.setStatus(PostcardState.APPROVED);
        updPostcard.setTransmissionState(TransmissionState.OPEN);
        postcardRepository.saveAndFlush(updPostcard);
        asyncPostcardApiCall.apiCallWithCardElements(updPostcard, CardElementType.APPROVE);
    }

    /*
     * updates the senderaddress
     *
     * @param id       - the postcard-id
     * @param adr      - the new address
     */

    @Transactional
    public void updateSender(Long id, AddressDTO adr) {
        Postcard updPostcard = postcardRepository.getOne(id);

        // exit when not allowed
        if (!isUpdateAllowed(updPostcard)) return;

        updPostcard.setSenderFirstname(adr.getFirstname());
        updPostcard.setSenderLastname(adr.getLastname());
        updPostcard.setSenderStreet(adr.getStreet());
        updPostcard.setSenderHousenr(adr.getHouseNr());
        updPostcard.setSenderZip(adr.getZip());
        updPostcard.setSenderCity(adr.getCity());

        updPostcard.setTransmissionState(TransmissionState.OPEN);
        postcardRepository.saveAndFlush(updPostcard);
        asyncPostcardApiCall.apiCallWithCardElements(updPostcard, CardElementType.SENDER);
    }

    /*
     * updates the recipientaddress
     *
     * @param id       - the postcard-id
     * @param adr      - the new address
     */

    @Transactional
    public void updateRecipient(Long id, AddressDTO adr) {
        Postcard updPostcard = postcardRepository.getOne(id);

        // exit when not allowed
        if (!isUpdateAllowed(updPostcard)) return;

        updPostcard.setRecipientFirstname(adr.getFirstname());
        updPostcard.setRecipientLastname(adr.getLastname());
        updPostcard.setRecipientStreet(adr.getStreet());
        updPostcard.setRecipientHousenr(adr.getHouseNr());
        updPostcard.setRecipientZip(adr.getZip());
        updPostcard.setRecipientCity(adr.getCity());

        updPostcard.setTransmissionState(TransmissionState.OPEN);
        postcardRepository.saveAndFlush(updPostcard);
        asyncPostcardApiCall.apiCallWithCardElements(updPostcard, CardElementType.RECIPIENT);
    }

    /*
     * updates the frontimage
     *
     * @param id       - the postcard-id
     * @param imageId  - the id of the new image
     */

    @Transactional
    public void updateFrontimage(Long id, Long imageId) {
        Postcard updPostcard = postcardRepository.getOne(id);

        // exit when not allowed
        if (!isUpdateAllowed(updPostcard)) return;

        Image img = imageRepository.getOne(imageId);

        updPostcard.setTransmissionState(TransmissionState.OPEN);
        updPostcard.setFrontimage(img);
        postcardRepository.saveAndFlush(updPostcard);
        asyncPostcardApiCall.apiCallWithCardElements(updPostcard, CardElementType.IMAGE);
    }

    /*
     * gets a preview over the postcard-API
     *
     * @param id       - the postcard-id
     * @param type     - front or back
     * @return preview as base64-encoded string
     */
    public String getPreview(Long id, String type) throws Exception {
        Postcard postcard = postcardRepository.getOne(id);
        PostCardApiCall call = postcardApiCallProvider.getNewPostCardApiCall(PostcardApiObjectConverter.toPostcardApiDto(postcard));
        PostCardApiCall.PostcardSide side = type.compareTo("front") == 0 ? PostCardApiCall.PostcardSide.FRONT : PostCardApiCall.PostcardSide.BACK;
        return call.getPreview(side);
    }

    /*
     * check if update is allowed
     *
     * @param postcard    - the postcard to be updated
     * @return true if allowed, false if not
     */
    private boolean isUpdateAllowed(Postcard postcard) {
        // update is only allowed when the state is open or if the User is logged-in
        if (postcard.getStatus().equals(PostcardState.OPEN)) {
            return true;
        } else {
            return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        }
    }


    /*
     * search for postcards
     *
     * @param campId        - the campaign-id, the postcards belong to
     * @param pageRequest   - the page of the search result
     * @param cardSearchDTO - additional parameters
     * @return result-page
     */
    public Page<PostcardDTO> findAllByCampaign(Long campId, PageRequest pageRequest, CardSearchDTO cardSearchDTO) {

        Specification<Postcard> spec = PostcardSpec.isFromCampaign(campId);

        if (cardSearchDTO.getFrom() != null) spec = spec.and(PostcardSpec.isDateAfter(cardSearchDTO.getFrom()));
        if (cardSearchDTO.getTill() != null) spec = spec.and(PostcardSpec.isDateBefore(cardSearchDTO.getTill()));
        if (cardSearchDTO.getState() != null) spec = spec.and(PostcardSpec.hasState(cardSearchDTO.getState()));
        if (cardSearchDTO.getTransmissionState() != null)
            spec = spec.and(PostcardSpec.hasTransmissionState(cardSearchDTO.getTransmissionState()));

        Page<PostcardDTO> pageResult = postcardRepository.findAll(
                Specification.where(spec),
                pageRequest
        ).map(PostcardDTO::new);

        return pageResult;

    }


    /*
     * export excel with postcards
     *
     * @param campId        - the campaign-id, the postcards belong to
     * @param cardSearchDTO - additional parameters
     * @param outputStream  - stream to write the excel file to
     */
    public void generateReportForCards(Long campId, CardSearchDTO cardSearchDTO, OutputStream outputStream) throws Exception {

        Specification<Postcard> spec = PostcardSpec.isFromCampaign(campId);

        if (cardSearchDTO.getFrom() != null) spec = spec.and(PostcardSpec.isDateAfter(cardSearchDTO.getFrom()));
        if (cardSearchDTO.getTill() != null) spec = spec.and(PostcardSpec.isDateBefore(cardSearchDTO.getTill()));
        if (cardSearchDTO.getState() != null) spec = spec.and(PostcardSpec.hasState(cardSearchDTO.getState()));
        if (cardSearchDTO.getTransmissionState() != null)
            spec = spec.and(PostcardSpec.hasTransmissionState(cardSearchDTO.getTransmissionState()));

        List<Postcard> result = postcardRepository.findAll(
                Specification.where(spec)
        );

        ReportGenerator generator = new ReportGenerator(result);

        Workbook workbook = generator.generateWorkbook();
        workbook.write(outputStream);


    }


}
