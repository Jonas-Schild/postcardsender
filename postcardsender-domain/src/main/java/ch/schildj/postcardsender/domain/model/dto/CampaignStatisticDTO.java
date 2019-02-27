package ch.schildj.postcardsender.domain.model.dto;

public class CampaignStatisticDTO {
    private String campaignKey;
    private int quota;
    private int sendPostcards;
    private int freeToSendPostcards;

    public String getCampaignKey() {
        return campaignKey;
    }

    public void setCampaignKey(String campaignKey) {
        this.campaignKey = campaignKey;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getSendPostcards() {
        return sendPostcards;
    }

    public void setSendPostcards(int sendPostcards) {
        this.sendPostcards = sendPostcards;
    }

    public int getFreeToSendPostcards() {
        return freeToSendPostcards;
    }

    public void setFreeToSendPostcards(int freeToSendPostcards) {
        this.freeToSendPostcards = freeToSendPostcards;
    }

    public CampaignStatisticDTO () {}

    public CampaignStatisticDTO (String campaignKey,int quota,int sendPostcards,int freeToSendPostcards) {
        this.campaignKey = campaignKey;
        this.quota = quota;
        this.sendPostcards = sendPostcards;
        this.freeToSendPostcards = freeToSendPostcards;
    }

}
