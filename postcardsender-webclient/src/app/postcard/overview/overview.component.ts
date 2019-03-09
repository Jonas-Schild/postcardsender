import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {StateService} from '../../services/state.service';
import {Postcard} from '../../models/postcard';
import {ImageService} from '../../services/image.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {PostcardService} from '../../services/postcard.service';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  @Output()
  approve: EventEmitter<any> = new EventEmitter();

  @Output()
  callStep: EventEmitter<number> = new EventEmitter<number>();

  public postcard: Postcard;
  public imageFile: ArrayBuffer | string;
  public prevImg: string;
  public prevLoading = false;
  public approveLoading = false;

  constructor(private stateService: StateService, private imageService: ImageService,
              private modalService: NgbModal, private postcardService: PostcardService) {
  }

  ngOnInit() {
    this.approveLoading = false;
    this.stateService.currentPostcard.subscribe(
      p => {
        this.postcard = p;
        if (p.frontImageId) {
          this.imageService.getImageFile(p.frontImageId).subscribe(image => {
            const reader = new FileReader();
            reader.addEventListener('load', () => {
              this.imageFile = reader.result;
            }, false);
            reader.readAsDataURL(image);
          });
        }
      }
    );
  }

  approveCard() {
    this.approveLoading = true;
    this.approve.emit();
    this.approveLoading = false;
  }

  goToStep(nr: number) {
    this.callStep.emit(nr);
  }

  openModal(content, side: string) {
    this.prevLoading = true;
    this.postcardService.getPreview(this.postcard.id, side).subscribe(base64Img => {
      this.prevImg = base64Img;
    }, error1 => {
      this.prevImg = undefined;
    }, () => {
      this.prevLoading = false;
    });
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'});
  }


}
