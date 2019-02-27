import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-imagepicker',
  templateUrl: './imagepicker.component.html',
  styleUrls: ['./imagepicker.component.css']
})
export class ImagepickerComponent implements OnInit {

  @Input()
  public images: Array<object>;

  @Output()
  public selectedImage: EventEmitter<number> = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit() {
  }

  chooseImage(id: number) {
    this.selectedImage.emit(id);
  }

}
