package ch.schildj.postcardsender.domain.model.dto;

import ch.schildj.postcardsender.domain.enums.BrandingType;
import ch.schildj.postcardsender.domain.enums.ImageType;
import ch.schildj.postcardsender.domain.model.Campaign;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

/**
 * Transferobject
 * Campaign
 */
public class CampaignDTO {
    private Long id;
    private String key;
    private String desc;
    private LocalDate validFrom;
    private LocalDate validTo;
    private ImageType imgType;
    private Integer maxCards;
    private BrandingType brandType;
    private String brandText;
    private String brandBlockColor;
    private String brandTextColor;
    private String brandQr;
    private Long brandImgId;
    private Long stampImgId;

    public CampaignDTO() {

    }


    public CampaignDTO(Long id, String key, String desc, LocalDate validFrom, LocalDate validTo, String imgType, Integer maxCards, String brandType,
                       String brandText, String brandBlockColor, String brandTextColor, String brandQr, Long stampImgId, Long brandImgId) {
        this.id = id;
        this.key = key;
        this.desc = desc;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.imgType = ImageType.valueOf(imgType);
        this.maxCards = maxCards;
        this.brandType = BrandingType.valueOf(brandType);
        this.brandText = brandText;
        this.brandBlockColor = brandBlockColor;
        this.brandTextColor = brandTextColor;
        this.brandQr = brandQr;
        this.stampImgId = stampImgId;
        this.brandImgId = brandImgId;
    }

    public CampaignDTO(Campaign campaign) {
        this.id = campaign.getId();
        this.key = campaign.getKey();
        this.desc = campaign.getDesc();
        this.validFrom = campaign.getValidFrom();
        this.validTo = campaign.getValidTo();
        this.imgType = campaign.getImgType();
        this.maxCards = campaign.getMaxCards();
        this.brandType = campaign.getBrandType();
        this.brandText = campaign.getBrandText();
        this.brandBlockColor = campaign.getBrandBlockColor();
        this.brandTextColor = campaign.getBrandTextColor();
        this.brandQr = campaign.getBrandQr();
        if (campaign.getStamp() != null) {
            this.stampImgId = campaign.getStamp().getId();
        }
        if (campaign.getBrandingImg() != null) {
            this.brandImgId = campaign.getBrandingImg().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getValidFrom() {
        return validFrom;
    }


    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getValidTo() {
        return validTo;
    }


    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public ImageType getImgType() {
        return imgType;
    }

    public void setImgType(ImageType imgType) {
        this.imgType = imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = ImageType.valueOf(imgType);
    }

    public Integer getMaxCards() {
        return maxCards;
    }

    public void setMaxCards(Integer maxCards) {
        this.maxCards = maxCards;
    }

    public BrandingType getBrandType() {
        return brandType;
    }

    public void setBrandType(BrandingType brandType) {
        this.brandType = brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = BrandingType.valueOf(brandType);
    }

    public String getBrandText() {
        return brandText;
    }

    public void setBrandText(String brandText) {
        this.brandText = brandText;
    }

    public String getBrandBlockColor() {
        return brandBlockColor;
    }

    public void setBrandBlockColor(String brandBlockColor) {
        this.brandBlockColor = brandBlockColor;
    }

    public String getBrandTextColor() {
        return brandTextColor;
    }

    public void setBrandTextColor(String brandTextColor) {
        this.brandTextColor = brandTextColor;
    }

    public String getBrandQr() {
        return brandQr;
    }

    public void setBrandQr(String brandQr) {
        this.brandQr = brandQr;
    }

    public Long getBrandImgId() {
        return brandImgId;
    }

    public void setBrandImgId(Long brandImgId) {
        this.brandImgId = brandImgId;
    }

    public Long getStampImgId() {
        return stampImgId;
    }

    public void setStampImgId(Long stampImgId) {
        this.stampImgId = stampImgId;
    }
}
