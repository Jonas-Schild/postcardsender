import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {StateService} from '../services/state.service';

@Component({
  selector: 'app-campaign',
  templateUrl: './campaign.component.html',
  styleUrls: ['./campaign.component.css']
})
export class CampaignComponent implements OnInit {

  public editMode = false;
  public newCampaign = false;

  constructor(private router: Router, private state: StateService) {
  }

  ngOnInit() {
  }

  showReport() {
    // update url-parameter
    this.router.navigate(['/ui/campaign/overview'], {
      queryParams: {
        campaignKey: this.state.currentCampaign.getValue().key
      }
    });
  }

  editCampaign() {
    this.newCampaign = false;
    this.editMode = true;
  }

  createNewCampaign() {
    this.newCampaign = true;
    this.state.createNewCampaign();
    this.editMode = true;
  }

  backFromEdit() {
    this.editMode = false;
  }

}
