export class Campaign {
  constructor(
    public id: number,
    public key: string,
    public desc: string,
    public validFrom: Date,
    public validTo: Date,
    public imgType: string,
    public maxCards: number,
    public brandType: string,
    public brandText: string,
    public brandBlockColor: string,
    public brandTextColor: string,
    public brandQr: string,
    public brandImgId: number,
    public stampImgId: number) {
  }
}
