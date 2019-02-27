import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Campaign} from '../../models/campaign';
import {NgForm} from '@angular/forms';
import {StateService} from '../../services/state.service';
import {CampaignService} from '../../services/campaign.service';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {ImageService} from '../../services/image.service';
import {ToastUtilService} from '../../services/toast-util.service';

@Component({
  selector: 'app-campaign-edit',
  templateUrl: './campaign-edit.component.html',
  styleUrls: ['./campaign-edit.component.css']
})
export class CampaignEditComponent implements OnInit {

  @Input()
  public newCampaign: boolean;

  @Output()
  back: EventEmitter<any> = new EventEmitter();

  constructor(private state: StateService, private campaignService: CampaignService, private ngbDateParserFormatter: NgbDateParserFormatter,
              private imageService: ImageService, private toastr: ToastUtilService) {
  }

  campaign: Campaign;
  campString: string;
  datePickerModelValidFrom: NgbDateStruct;
  datePickerModelValidTo: NgbDateStruct;
  images: Array<Object>;
  stamp: Object;
  brandImg: Object;


  ngOnInit() {
    this.state.currentCampaign.subscribe(nextVal => {
      this.campaign = nextVal;
      this.campString = JSON.stringify(this.campaign);
      this.datePickerModelValidFrom = this.getNgbDate(this.convertToDate(this.campaign.validFrom));
      this.datePickerModelValidTo = this.getNgbDate(this.convertToDate(this.campaign.validTo));
      this.showImages();
      this.showStamp();
      this.showBrandImg();
    });
  }

  handleSubmit(campaignForm: NgForm) {
    // this.userService.login(loginForm.value.username, loginForm.value.password);
    console.log('submited');
    this.campaign.validTo = new Date(this.datePickerModelValidTo.year,
      this.datePickerModelValidTo.month - 1, this.datePickerModelValidTo.day, 12);
    this.campaign.validFrom = new Date(this.datePickerModelValidFrom.year,
      this.datePickerModelValidFrom.month - 1, this.datePickerModelValidFrom.day, 12);
    this.campaignService.saveCampaign(this.campaign).subscribe(campid => {
      this.campaign.id = campid;
      this.toastr.toastSuccess('Gespeichert', 'Kampagne gespeichert');
      if (this.newCampaign) {
        this.newCampaign = false;
      } else {
        this.goBack();
      }
    });

  }

  goBack() {
    this.back.emit();
  }

  convertToDate(value: any) {
    let retVal;
    if (value instanceof Date) {
      retVal = value;
    } else {
      retVal = new Date(value);
    }
    return retVal;
  }


  public getNgbDate(dateIn: Date): NgbDateStruct {
    if (dateIn == null || undefined) {
      return null;
    }
    const ngbDateStruct = this.ngbDateParserFormatter.parse(
      dateIn.getDate() + '.' + (dateIn.getMonth() + 1) + '.' + dateIn.getFullYear());
    return ngbDateStruct;
  }


  public showImages() {
    if (this.campaign.id != null) {
      this.campaignService.getImages(this.campaign.id).subscribe(imageIds => {
          this.images = new Array<object>();
          for (const id of imageIds) {
            this.imageService.getImageFile(id).subscribe(image => {
              const reader = new FileReader();
              reader.addEventListener('load', () => {
                this.images.push({'id': id, 'file': reader.result});
              }, false);
              reader.readAsDataURL(image);
            });
          }
        }
      );
    }
  }

  public showStamp() {
    if (this.campaign.stampImgId != null) {
      this.imageService.getImageFile(this.campaign.stampImgId).subscribe(image => {
        const reader = new FileReader();
        reader.addEventListener('load', () => {
          this.stamp = reader.result;
        }, false);
        reader.readAsDataURL(image);
      });
    } else {
      this.stamp = null;
    }
  }

  public showBrandImg() {
    if (this.campaign.brandImgId != null) {
      this.imageService.getImageFile(this.campaign.brandImgId).subscribe(image => {
        const reader = new FileReader();
        reader.addEventListener('load', () => {
          this.brandImg = reader.result;
        }, false);
        reader.readAsDataURL(image);
      });
    } else {
      this.brandImg = null;
    }
  }

  public setStamp(imgId: number) {
    this.campaign.stampImgId = imgId;
    this.campaignService.addStamp(this.campaign.id, imgId).subscribe(nextVal => this.showStamp());
  }

  public removeStamp() {
    this.campaign.stampImgId = null;
    this.stamp = null;
    this.campaignService.removeStamp(this.campaign.id).subscribe();
  }

  public addImage(imgId: number) {
    console.log('addImage' + imgId);
    this.campaignService.addImage(this.campaign.id, imgId).subscribe(nextVal => this.showImages());
  }

  public removeImage(imgId: number) {
    this.campaignService.removeImage(this.campaign.id, imgId).subscribe(nextVal => this.showImages());

  }


}
