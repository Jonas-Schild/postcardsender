import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PostcardService} from '../../../services/postcard.service';
import {Cardhistory} from '../../../models/cardhistory';

@Component({
  selector: 'app-card-history',
  templateUrl: './card-history.component.html',
  styleUrls: ['./card-history.component.css']
})
export class CardHistoryComponent implements OnInit {

  @Input()
  id: number;

  @Output()
  backFromHist: EventEmitter<any> = new EventEmitter();

  public cardHistories: Array<Cardhistory>;

  constructor(private postcardService: PostcardService) {
  }

  ngOnInit() {
    this.postcardService.getCardHistory(this.id).subscribe(next => this.cardHistories = next);
  }

  back() {
    this.backFromHist.emit();
  }

}
