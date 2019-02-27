import {RespMessage} from './resp-message';

export class Cardhistory {
  constructor(
    public request: string,
    public respHttpCode: number,
    public respHttpError: string,
    public requestDate: Date,
    public respMessageDTOS?: Array<RespMessage>
  ) {

  }


}
