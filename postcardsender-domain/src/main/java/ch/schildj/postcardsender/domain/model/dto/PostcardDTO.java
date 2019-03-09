package ch.schildj.postcardsender.domain.model.dto;

import ch.schildj.postcardsender.domain.converter.LocalDateTimeSerializer;
import ch.schildj.postcardsender.domain.model.Postcard;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

/**
 * Transferobject
 * Postcard
 */
public class PostcardDTO {
    private Long id;
    private String key;
    private String text;
    private String senderFirstname;
    private String senderLastname;
    private String senderStreet;
    private String senderHousenr;
    private String senderZip;
    private String senderCity;
    private String recipientFirstname;
    private String recipientLastname;
    private String recipientStreet;
    private String recipientHousenr;
    private String recipientZip;
    private String recipientCity;
    private LocalDateTime mdate;
    private Long frontImageId;
    private Long campaignId;
    private String state;
    private String transmissionState;

    public PostcardDTO() {
        // Default constructor
    }


    public PostcardDTO(Long id, String key, String text, String senderFirstname, String senderLastname, String senderStreet, String senderHousenr, String senderZip, String senderCity, String recipientFirstname, String recipientLastname, String recipientStreet, String recipientHousenr, String recipientZip, String recipientCity, LocalDateTime mdate, Long frontImageId, Long campaignId, String state, String transmissionState) {
        this.id = id;
        this.key = key;
        this.text = text;
        this.senderFirstname = senderFirstname;
        this.senderLastname = senderLastname;
        this.senderStreet = senderStreet;
        this.senderHousenr = senderHousenr;
        this.senderZip = senderZip;
        this.senderCity = senderCity;
        this.recipientFirstname = recipientFirstname;
        this.recipientLastname = recipientLastname;
        this.recipientStreet = recipientStreet;
        this.recipientHousenr = recipientHousenr;
        this.recipientZip = recipientZip;
        this.recipientCity = recipientCity;
        this.mdate = mdate;
        this.frontImageId = frontImageId;
        this.campaignId = campaignId;
        this.state = state;
        this.transmissionState = transmissionState;
    }

    public PostcardDTO(Postcard postcard) {
        this.id = postcard.getId();
        this.key = postcard.getKey();
        this.text = postcard.getText();
        this.senderFirstname = postcard.getSenderFirstname();
        this.senderLastname = postcard.getSenderLastname();
        this.senderStreet = postcard.getSenderStreet();
        this.senderHousenr = postcard.getSenderHousenr();
        this.senderZip = postcard.getSenderZip();
        this.senderCity = postcard.getSenderCity();
        this.recipientFirstname = postcard.getRecipientFirstname();
        this.recipientLastname = postcard.getRecipientLastname();
        this.recipientStreet = postcard.getRecipientStreet();
        this.recipientHousenr = postcard.getRecipientHousenr();
        this.recipientZip = postcard.getRecipientZip();
        this.recipientCity = postcard.getRecipientCity();
        this.mdate = postcard.getLastModification();
        if (postcard.getFrontimage() != null) {
            this.frontImageId = postcard.getFrontimage().getId();
        }
        if (postcard.getCampaign() != null) {
            this.campaignId = postcard.getCampaign().getId();
        }
        this.state = postcard.getStatus().name();
        this.transmissionState = postcard.getTransmissionState().name();

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderFirstname() {
        return senderFirstname;
    }

    public void setSenderFirstname(String senderFirstname) {
        this.senderFirstname = senderFirstname;
    }

    public String getSenderLastname() {
        return senderLastname;
    }

    public void setSenderLastname(String senderLastname) {
        this.senderLastname = senderLastname;
    }

    public String getSenderStreet() {
        return senderStreet;
    }

    public void setSenderStreet(String senderStreet) {
        this.senderStreet = senderStreet;
    }

    public String getSenderHousenr() {
        return senderHousenr;
    }

    public void setSenderHousenr(String senderHousenr) {
        this.senderHousenr = senderHousenr;
    }

    public String getSenderZip() {
        return senderZip;
    }

    public void setSenderZip(String senderZip) {
        this.senderZip = senderZip;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getRecipientFirstname() {
        return recipientFirstname;
    }

    public void setRecipientFirstname(String recipientFirstname) {
        this.recipientFirstname = recipientFirstname;
    }

    public String getRecipientLastname() {
        return recipientLastname;
    }

    public void setRecipientLastname(String recipientLastname) {
        this.recipientLastname = recipientLastname;
    }

    public String getRecipientStreet() {
        return recipientStreet;
    }

    public void setRecipientStreet(String recipientStreet) {
        this.recipientStreet = recipientStreet;
    }

    public String getRecipientHousenr() {
        return recipientHousenr;
    }

    public void setRecipientHousenr(String recipientHousenr) {
        this.recipientHousenr = recipientHousenr;
    }

    public String getRecipientZip() {
        return recipientZip;
    }

    public void setRecipientZip(String recipientZip) {
        this.recipientZip = recipientZip;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getMdate() {
        return mdate;
    }

    public void setMdate(LocalDateTime mdate) {
        this.mdate = mdate;
    }

    public Long getFrontImageId() {
        return frontImageId;
    }

    public void setFrontImageId(Long frontImageId) {
        this.frontImageId = frontImageId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTransmissionState() {
        return transmissionState;
    }

    public void setTransmissionState(String transmissionState) {
        this.transmissionState = transmissionState;
    }
}
