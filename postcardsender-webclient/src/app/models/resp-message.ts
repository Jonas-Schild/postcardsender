export class RespMessage {
  constructor(
    public type: string,
    public code: number,
    public text: string,
    public cdate: Date
  ) {

  }


}
