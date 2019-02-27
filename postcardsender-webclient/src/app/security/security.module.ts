import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {UserService} from './user.service';
import {AuthGuard} from './auth.guard';
import {FormsModule} from '@angular/forms';

@NgModule({
    declarations: [
        LoginComponent,
    ],
    imports: [
        CommonModule,
        FormsModule
    ],
    providers: [
        AuthGuard,
        UserService
    ]
})
export class SecurityModule {}
