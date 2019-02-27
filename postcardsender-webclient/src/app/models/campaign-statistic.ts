export class CampaignStatistic {
  constructor(
    public campaignKey: string,
    public quota: number,
    public sendPostcards: number,
    public freeToSendPostcards: number) {
  }
}

