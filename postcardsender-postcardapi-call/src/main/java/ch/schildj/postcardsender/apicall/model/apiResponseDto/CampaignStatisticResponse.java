package ch.schildj.postcardsender.apicall.model.apiResponseDto;

/**
 * Transferobject to receive the response from the Postcard API of Post CH
 * Campaign Statistic
 */
public class CampaignStatisticResponse {
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
}
