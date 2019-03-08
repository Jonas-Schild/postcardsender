import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Campaign} from '../models/campaign';
import {Postcard} from '../models/postcard';
import {CampaignService} from './campaign.service';

@Injectable()
export class StateService {
  public adminMode = new BehaviorSubject<boolean>(false);
  public currentCampaign = new BehaviorSubject<Campaign>(this.newCampaign());
  public currentPostcard = new BehaviorSubject<Postcard>(this.newCard());

  constructor(private campaignService: CampaignService) {
  }

  public setCampaign(c: Campaign) {
    this.currentCampaign.next(c);
  }

  public setPostcard(p: Postcard) {
    this.currentPostcard.next(p);
  }

  public setAdminMode(m: boolean) {
    this.adminMode.next(m);
  }

  public setCampaignByKey(key: string) {
    if (this.currentCampaign.getValue().key !== key) {
      this.campaignService.getCampaignByKey(key).subscribe(camp => this.currentCampaign.next(camp));
    }
  }

  public createNewCard() {
    this.setPostcard(this.newCard());
  }

  public createNewCampaign() {
    this.setCampaign(this.newCampaign());
  }

  public newCard() {
    return new Postcard(null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      this.currentCampaign.getValue().id,
      null,
      null);
  }

  public newCampaign() {
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const day = today.getDate();
    const inOneYear = new Date(year + 1, month, day);
    return new Campaign(null,
      null,
      null,
      today,
      inOneYear,
      'PREDEFINED',
      50,
      'TEXT',
      null,
      null,
      null,
      null,
      null,
      null);
  }


}
