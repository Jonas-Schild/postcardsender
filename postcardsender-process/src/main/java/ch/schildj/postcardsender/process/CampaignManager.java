package ch.schildj.postcardsender.process;

import ch.schildj.postcardsender.apicall.process.PostCardApiCall;
import ch.schildj.postcardsender.apicall.process.PostcardApiCallProvider;
import ch.schildj.postcardsender.domain.model.CampImageSet;
import ch.schildj.postcardsender.domain.model.Campaign;
import ch.schildj.postcardsender.domain.model.Image;
import ch.schildj.postcardsender.domain.model.dto.CampaignDTO;
import ch.schildj.postcardsender.domain.model.dto.CampaignStatisticDTO;
import ch.schildj.postcardsender.domain.repository.CampImageSetRepository;
import ch.schildj.postcardsender.domain.repository.CampaignRepository;
import ch.schildj.postcardsender.domain.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Class CampaignManager.
 * Manage the campaign
 */
@Component
public class CampaignManager {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PostcardApiCallProvider postcardApiCallProvider;

    @Autowired
    private CampImageSetRepository campImageSetRepository;

    @Autowired
    private ImageRepository imageRepository;

    private LocalDate currentDate = LocalDate.now();

    /*
     * converts a campaign-DTO to an entity
     *
     * @param campaignDTO       - the transfer object
     * @return the entity
     */
    private Campaign toEntity(CampaignDTO campaignDTO) {
        Campaign c;
        if (campaignDTO.getId() == null) {
            c = new Campaign();
        } else {
            c = campaignRepository.getOne(campaignDTO.getId());
        }

        c.setDesc(campaignDTO.getDesc());
        c.setKey(campaignDTO.getKey());
        c.setValidFrom(campaignDTO.getValidFrom());
        c.setValidTo(campaignDTO.getValidTo());
        c.setMaxCards(campaignDTO.getMaxCards());
        c.setImgType(campaignDTO.getImgType());

        c.setBrandType(campaignDTO.getBrandType());

        c.setBrandBlockColor(campaignDTO.getBrandBlockColor());
        c.setBrandTextColor(campaignDTO.getBrandTextColor());

        switch (campaignDTO.getBrandType()) {
            case TEXT:
                c.setBrandText(campaignDTO.getBrandText());
                c.setBrandQr(null);
                c.setBrandingImg(null);
                break;
            case IMAGE:
                c.setBrandText(null);
                c.setBrandQr(null);
                if (campaignDTO.getBrandImgId() != null) {
                    c.setBrandingImg(this.imageRepository.getOne(campaignDTO.getBrandImgId()));
                }
                break;
            case QR:
                c.setBrandText(campaignDTO.getBrandText());
                c.setBrandQr(campaignDTO.getBrandQr());
                c.setBrandingImg(null);
                break;
            default:
                c.setBrandText(null);
                c.setBrandQr(null);
                c.setBrandingImg(null);
                break;
        }

        if (campaignDTO.getStampImgId() != null) {
            c.setStamp(imageRepository.getOne(campaignDTO.getStampImgId()));
        }

        return c;
    }

    /*
     * get the statistics for a campaign
     *
     * @param campId       - the id of the campaign
     * @return the statistic
     */
    public CampaignStatisticDTO getStatistic(Long campId) throws Exception {
        Campaign c = campaignRepository.getOne(campId);
        PostCardApiCall call = postcardApiCallProvider.getNewPostCardApiCall(c.getKey());
        return PostcardApiObjectConverter.toCampaignStatisticDTO(call.getCampaignStatistic());
    }

    /*
     * remove an image from a campaign
     *
     * @param campId       - the id of the campaign
     * @param imgId        - the id of the image
     */
    @Transactional
    public void deleteImages(Long campId, Long imgId) {
        List<CampImageSet> cis = campImageSetRepository.findAllByCampIdAndImageId(campId, imgId);
        if (cis != null && cis.size() > 0) {
            for (CampImageSet c : cis) {
                campImageSetRepository.delete(c);
            }
        }
        campImageSetRepository.flush();

    }

    /*
     * adds an image to a campaign
     *
     * @param campId       - the id of the campaign
     * @param imgId        - the id of the image
     */
    @Transactional
    public void addImage(Long campId, Long imgId) {
        CampImageSet cis = new CampImageSet();
        Campaign c = campaignRepository.getOne(campId);
        Image i = imageRepository.getOne(imgId);
        cis.setCampaign(c);
        cis.setImage(i);
        campImageSetRepository.saveAndFlush(cis);
    }

    /*
     * remove a stamp from a campaign
     *
     * @param campId       - the id of the campaign
     */
    @Transactional
    public void deleteStamp(Long campId) {
        Campaign c = campaignRepository.getOne(campId);
        c.setStamp(null);
        campaignRepository.saveAndFlush(c);
    }

    /*
     * add a stamp to a campaign
     *
     * @param campId       - the id of the campaign
     * @param imgId        - the id of the image
     */
    @Transactional
    public void addStamp(Long campId, Long imgId) {
        Campaign c = campaignRepository.getOne(campId);
        c.setStamp(imageRepository.getOne(imgId));
        campaignRepository.saveAndFlush(c);
    }

    /*
     * save a campaign
     *
     * @param campaignDTO   - transferobject of a campaign
     * @return the id of the saved campaign
     */
    @Transactional
    public Long saveCampaign(CampaignDTO campaignDTO) {
        Campaign c = this.toEntity(campaignDTO);
        campaignRepository.saveAndFlush(c);
        return c.getId();
    }

    ;


}
