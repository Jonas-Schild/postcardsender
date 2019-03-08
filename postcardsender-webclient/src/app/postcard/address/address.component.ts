import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Address} from '../../models/address';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {

  @Input()
  address: Address;

  @Input()
  addresstyp: string;

  @Output()
  save: EventEmitter<Address> = new EventEmitter<Address>();


  constructor() {
  }

  ngOnInit() {
  }

  emit() {
    this.save.emit(this.address);
  }

}
