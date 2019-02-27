package ch.schildj.postcardsender.apicall.model;

/*
 * Transferobject between the process-component and the apicall-component
 * it holds all postcard elements
 */

public class PostcardApiDto {

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
    private Long campaignId;
    private String campKey;
    private String brandText;
    private String brandBlockColor;
    private String brandTextColor;
    private String brandQr;
    private byte[] frontImage;
    private byte[] brandImage;
    private byte[] stampImage;


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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampKey() {
        return campKey;
    }

    public void setCampKey(String campKey) {
        this.campKey = campKey;
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

    public byte[] getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(byte[] frontImage) {
        this.frontImage = frontImage;
    }

    public byte[] getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(byte[] brandImage) {
        this.brandImage = brandImage;
    }

    public byte[] getStampImage() {
        return stampImage;
    }

    public void setStampImage(byte[] stampImage) {
        this.stampImage = stampImage;
    }
}


