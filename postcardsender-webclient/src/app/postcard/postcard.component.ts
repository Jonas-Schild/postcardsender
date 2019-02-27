import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Postcard} from '../models/postcard';
import {StateService} from '../services/state.service';
import {Address} from '../models/address';
import {ActivatedRoute} from '@angular/router';
import {Campaign} from '../models/campaign';
import {ImageService} from '../services/image.service';
import {CampaignService} from '../services/campaign.service';
import {ToastUtilService} from '../services/toast-util.service';
import {PostcardService} from '../services/postcard.service';

@Component({
  selector: 'app-postcard',
  templateUrl: './postcard.component.html',
  styleUrls: ['./postcard.component.css']
})
export class PostcardComponent implements OnInit {

  postcard: Postcard;
  campaign: Campaign;
  recipient: Address;
  sender: Address;
  images: Array<object>;
  calledFromOverview = false;

  creationStep: number;


  constructor(private stateService: StateService, private imageService: ImageService, private postcardService: PostcardService,
              private campaignService: CampaignService, private route: ActivatedRoute, private toastr: ToastUtilService) {
  }

  @Input()
  public calledFromCampaign = false;

  @Output()
  public action: EventEmitter<any> = new EventEmitter();

  ngOnInit() {
    if (this.calledFromCampaign) {
      this.creationStep = 5;
    } else {
      this.calledFromOverview = false;
      this.route.queryParams.subscribe(params => {
          if (!!params.campaignKey && params.campaignKey) {
            this.stateService.setCampaignByKey(params.campaignKey);
          }
          if (!!params.creationStep && params.creationStep) {
            this.creationStep = +params.creationStep; // (+) converts string 'creationStep' to a number
          } else {
            this.creationStep = 1;
          }
        }
      );
    }

    this.stateService.currentCampaign.subscribe(camp => {
      this.campaign = camp;
      if (camp.id != null) {
        // get current postcard
        this.stateService.currentPostcard.subscribe(postcard => {
            this.postcard = postcard;
            this.postcard.campaignId = camp.id;
            this.recipient = new Address(
              postcard.recipientFirstname,
              postcard.recipientLastname,
              postcard.recipientStreet,
              postcard.recipientHousenr,
              postcard.recipientZip,
              postcard.recipientCity);
            this.sender = new Address(postcard.senderFirstname,
              postcard.senderLastname,
              postcard.senderStreet,
              postcard.senderHousenr,
              postcard.senderZip,
              postcard.senderCity);
          }
        )
        ;

        // get images
        if (camp.imgType === 'PREDEFINED') {
          this.campaignService.getImages(camp.id).subscribe(imageIds => {
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
    })
    ;


  }


  next() {
    if (this.calledFromOverview) {
      this.creationStep = 5;
    } else {
      this.creationStep++;
    }
    if (this.creationStep === 5 && !this.calledFromOverview) {
      // send postcard to backend
      this.postcardService.saveNewPostcard(this.postcard).subscribe(id => {
        this.postcard.id = id;
      });
    }
    this.calledFromOverview = false;
  }


  updateRecipient(adr: Address) {
    this.recipient = adr;
    this.postcard.recipientFirstname = adr.firstname;
    this.postcard.recipientLastname = adr.lastname;
    this.postcard.recipientStreet = adr.street;
    this.postcard.recipientHousenr = adr.houseNr;
    this.postcard.recipientZip = adr.zip;
    this.postcard.recipientCity = adr.city;
    if (this.calledFromOverview) {
      this.postcardService.updateRecipient(this.postcard.id, adr).subscribe(next => this.next());
    } else {
      this.next();
    }
  }

  updateSender(adr: Address
  ) {
    this.sender = adr;
    this.postcard.senderFirstname = adr.firstname;
    this.postcard.senderLastname = adr.lastname;
    this.postcard.senderStreet = adr.street;
    this.postcard.senderHousenr = adr.houseNr;
    this.postcard.senderZip = adr.zip;
    this.postcard.senderCity = adr.city;
    if (this.calledFromOverview) {
      this.postcardService.updateSender(this.postcard.id, adr).subscribe(next => this.next());
    } else {
      this.next();
    }
  }

  approvePostcard() {
    this.postcard.campaignId = this.campaign.id;
    // send approval to backend
    this.postcardService.approve(this.postcard.id).subscribe(next => {
      this.toastr.toastSuccess('Herzlichen Dank für das versenden der Postkarte', 'Postkarte übermittelt');
      this.next();
      if (this.calledFromCampaign) {
        this.action.emit();
      }

    });

  }


  selectImage(id: number
  ) {
    this.postcard.frontImageId = id;
    if (this.calledFromOverview) {
      this.postcardService.updateFrontImage(this.postcard.id, id).subscribe(next => this.next());
    } else {
      this.next();
    }
  }

  updateText() {
    if (this.calledFromOverview) {
      this.postcardService.updateText(this.postcard.id, this.postcard.text).subscribe(next => this.next());
    } else {
      this.next();
    }
  }

  callStepFromOverview(nr: number
  ) {
    this.creationStep = nr;
    // only set true when postcard already saved
    this.calledFromOverview = (!!this.postcard.id);
  }

  newCard() {
    this.stateService.createNewCard();
    this.creationStep = 1;
    this.calledFromOverview = false;
  }

  backToCampaign() {
    this.action.emit();
  }

}
