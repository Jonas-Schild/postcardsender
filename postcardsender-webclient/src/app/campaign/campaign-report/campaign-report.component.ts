import {Component, OnInit} from '@angular/core';
import {StateService} from '../../services/state.service';
import {CampaignService} from '../../services/campaign.service';
import {Campaign} from '../../models/campaign';
import {CampaignStatistic} from '../../models/campaign-statistic';

@Component({
  selector: 'app-campaign-report',
  templateUrl: './campaign-report.component.html',
  styleUrls: ['./campaign-report.component.css']
})
export class CampaignReportComponent implements OnInit {

  public campaign: Campaign;
  public statistic: CampaignStatistic;

  constructor(private campaignService: CampaignService, private stateService: StateService) {
  }

  ngOnInit() {
    this.stateService.currentCampaign.subscribe(next => {
      this.campaign = next;
      this.campaignService.getCampaignStatistic(this.campaign.id).subscribe(
        stat => {
          this.statistic = stat;
        },
        error => {
          this.statistic = undefined;
        }
      );
    });

  }

}
