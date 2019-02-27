import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {UserService} from '../user.service';
import {NgForm} from '@angular/forms';

@Component({
    selector: 'app-login',
    templateUrl: 'login.component.html',
})
export class LoginComponent implements OnInit {

    status: String;
    statusText: String;

    constructor(private userService: UserService, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route
            .queryParams
            .subscribe(params => {
                this.status = params['status'];
                this.statusText = params['statusText'];
            });
    }

    handleSubmit(loginForm: NgForm) {
        this.userService.login(loginForm.value.username, loginForm.value.password);
    }
}
