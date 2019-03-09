import {Component, OnInit} from '@angular/core';
import {StateService} from '../../services/state.service';
import {CampaignService} from '../../services/campaign.service';
import {Campaign} from '../../models/campaign';
import {Postcard} from '../../models/postcard';
import {ActivatedRoute} from '@angular/router';
import {CardSearch} from '../../models/card-search';
import {ToastUtilService} from '../../services/toast-util.service';

@Component({
  selector: 'app-campaign-card-overview',
  templateUrl: './campaign-card-overview.component.html',
  styleUrls: ['./campaign-card-overview.component.css']
})
export class CampaignCardOverviewComponent implements OnInit {

  constructor(private stateService: StateService, private campaignService: CampaignService,
              private route: ActivatedRoute, private toastr: ToastUtilService) {
  }

  public initializeFinished = false;

  public campaign: Campaign;

  public postcardList: Array<Postcard>;

  public page: number;
  public maxPage: number;
  public postCardId: number;

  public collectionSize: number;
  public showPostcard = false;
  public showPostcardHist = false;

  public search: CardSearch;

  public loading = false;

  public PAGESIZE = 10;

  ngOnInit() {
    this.page = 0;

    this.search = new CardSearch(undefined, undefined, undefined, undefined);

    this.route.queryParams.subscribe(params => {
        if (!!params.campaignKey && params.campaignKey) {
          this.stateService.setCampaignByKey(params.campaignKey);
        }
      }
    );

    this.stateService.currentCampaign.subscribe(camp => {
      this.campaign = camp;
      if (this.campaign.id) {
        this.loadPostcards();
      }
    });


  }

  /**
   * Load requested Postcards
   */
  loadPostcards() {
    this.initializeFinished = false;
    this.showPostcard = false;
    this.campaignService.getPostcardsForCampaign(this.campaign.id, this.page, this.search).subscribe(result => {
      if (result) {
        this.collectionSize = result.dataSize;
        if (this.collectionSize > 0) {
          this.maxPage = Math.ceil(this.collectionSize / this.PAGESIZE);
        } else {
          this.maxPage = 1;
        }

        this.postcardList = result.data;
      } else {
        this.postcardList = [];
      }
      this.initializeFinished = true;


    });
  }

  setPage(page: any) {
    this.page = page.offset;
    this.loadPostcards();
  }

  goToOverviewWithCard(postcard: Postcard) {
    this.stateService.setPostcard(postcard);
    this.showPostcard = true;
  }

  backFromPostcard() {
    this.showPostcard = false;
    this.loadPostcards();
  }

  showHist(id: number) {
    this.postCardId = id;
    this.showPostcardHist = true;

  }

  backFromHist() {
    this.showPostcardHist = false;
  }

  /**
   * Downloads the requested report
   */
  public downloadReport() {
    this.loading = true;
    const filename = 'Postcards.xlsx';
    this.campaignService.downloadPostcardReport(this.campaign.id, this.search).subscribe(
      (result) => this.downloadResult(result, filename), (err) => {
        this.toastr.handleError(err);
      },
      () => this.loading = false);
  }

  /**
   * Downloads a blob with the given filename
   */
  private downloadResult(result: Blob, filename: string) {
    if (result) {
      // for IE and Edge
      if (window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(result, filename);
      } else {
        // for chrome etc.
        const link = document.createElement('a');
        const url = window.URL.createObjectURL(result);
        link.href = url;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
      }
    }
  }


}
