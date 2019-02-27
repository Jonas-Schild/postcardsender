import {Component, OnInit} from '@angular/core';
import {CampaignService} from '../services/campaign.service';
import {Campaign} from '../models/campaign';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  campaigns: Campaign[];
  selectedCampaign: Campaign;

  private url = 'ui/postcard';

  constructor(private campaignService: CampaignService, private router: Router) {
  }

  ngOnInit() {
  }

  selectCampaign(c: Campaign) {
    this.selectedCampaign = c;
    this.updateUrl();
  }

  private updateUrl(): void {
    // update url-parameter
    this.router.navigate([this.url], {
      queryParams: {
        campaignKey: this.selectedCampaign.key
      }
    });
  }

}
