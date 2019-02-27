import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Campaign} from '../../models/campaign';
import {CampaignService} from '../../services/campaign.service';
import {StateService} from '../../services/state.service';

@Component({
  selector: 'app-campaign-choose',
  templateUrl: './campaign-choose.component.html',
  styleUrls: ['./campaign-choose.component.css']
})
export class CampaignChooseComponent implements OnInit {

  campaigns: Campaign[];

  @Input()
  adminMode = false;

  @Output()
  selectedCampaign: EventEmitter<Campaign> = new EventEmitter<Campaign>();

  @Output()
  edit: EventEmitter<Campaign> = new EventEmitter<Campaign>();

  constructor(private campaignService: CampaignService, private stateService: StateService) {
  }

  ngOnInit() {
    if (this.adminMode) {
      this.campaignService.getAllCampaigns().subscribe(next => this.campaigns = next);
    } else {
      // Show only active Campaigns
      this.campaignService.getAllCurrentcampaigns().subscribe(next => this.campaigns = next);
    }
  }

  selectCampaign(c: Campaign) {
    this.stateService.setCampaign(c);
    this.selectedCampaign.emit(c);
  }

  editCampaign(c: Campaign) {
    this.stateService.setCampaign(c);
    this.edit.emit(c);
  }


}
