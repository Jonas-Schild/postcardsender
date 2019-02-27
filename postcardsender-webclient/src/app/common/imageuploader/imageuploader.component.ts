import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ImageService} from '../../services/image.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {ImageCroppedEvent, ImageCropperComponent} from 'ngx-image-cropper';

@Component({
  selector: 'app-imageuploader',
  templateUrl: './imageuploader.component.html',
  styleUrls: ['./imageuploader.component.css']
})
export class ImageuploaderComponent implements OnInit {

  @ViewChild(ImageCropperComponent) imageCropper: ImageCropperComponent;

  @Input()
  showCrop = true;

  @Output()
  uploadedFile: EventEmitter<number> = new EventEmitter<number>();

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = {percentage: 0};

  imageChangedEvent: any = '';
  croppedImage: any = '';
  showCropper = false;
  imagePreview: any = '';
  loading = false;


  constructor(private imageService: ImageService) {
  }

  ngOnInit() {
    this.selectedFiles = undefined;
  }

  selectFile(event) {
    this.loading = true;
    this.selectedFiles = event.target.files;
    this.progress = {percentage: 0};
    this.imageChangedEvent = event;
    if (!this.showCrop) {
      this.showPreview(this.selectedFiles.item(0));
    }
  }

  upload() {
    this.progress.percentage = 0;

    if (!this.showCrop) {
      this.currentFileUpload = this.selectedFiles.item(0);
    } else {
      this.currentFileUpload = this.croppedImage;
    }
    //
    this.imageService.saveImage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('upload ok, id = ' + event.body);
        this.uploadedFile.emit(+event.body);
      }
    });

    this.selectedFiles = undefined;
    this.imagePreview = undefined;
    this.croppedImage = '';
    this.imageChangedEvent = '';
  }

  //

  showPreview(imageFile) {
    console.log('showPreview');
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imagePreview = reader.result;
    }, false);
    reader.readAsDataURL(imageFile);
    this.loading = false;
  }


  fileChangeEvent(event: any): void {
    console.log('fileChange');
    this.imageChangedEvent = event;
  }

  imageCropped(event: ImageCroppedEvent) {
    console.log('imageCropped');
    this.croppedImage = event.file;
    this.showPreview(this.croppedImage);
  }

  imageLoaded() {
    this.showCropper = true;
    console.log('Image loaded');
  }

  cropperReady() {
    console.log('Cropper ready');
  }

  loadImageFailed() {
    console.log('Load failed');
  }

  rotateLeft() {
    this.imageCropper.rotateLeft();
  }

  rotateRight() {
    this.imageCropper.rotateRight();
  }

  flipHorizontal() {
    this.imageCropper.flipHorizontal();
  }

  flipVertical() {
    this.imageCropper.flipVertical();
  }

}
