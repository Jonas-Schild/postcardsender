import {Component, OnInit} from '@angular/core';
import {UserService} from '../../security/user.service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styles: []
})
export class NavigationBarComponent implements OnInit {

  constructor(private userService: UserService) {
  }


  ngOnInit() {
  }

  logout() {
    this.userService.logout();
  }

}
