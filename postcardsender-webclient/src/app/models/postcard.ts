export class Postcard {
  constructor(
    public id: number,
    public key: string,
    public text: string,
    public senderFirstname: string,
    public senderLastname: string,
    public senderStreet: string,
    public senderHousenr: string,
    public senderZip: string,
    public senderCity: string,
    public recipientFirstname: string,
    public recipientLastname: string,
    public recipientStreet: string,
    public recipientHousenr: string,
    public recipientZip: string,
    public recipientCity: string,
    public mdate: Date,
    public frontImageId: number,
    public campaignId: number,
    public state: string,
    public transmissionState: string) {
  }

}

