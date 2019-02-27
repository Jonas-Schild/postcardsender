import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './security/login/login.component';
import {AuthGuard} from './security/auth.guard';
import {CampaignComponent} from './campaign/campaign.component';
import {PostcardComponent} from './postcard/postcard.component';
import {CampaignEditComponent} from './campaign/campaign-edit/campaign-edit.component';
import {CampaignCardOverviewComponent} from './campaign/campaign-card-overview/campaign-card-overview.component';

const routes: Routes = [
  {path: '', redirectTo: 'ui/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {
    path: 'ui',
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'postcard', component: PostcardComponent},
      {
        path: 'campaign', canActivate: [AuthGuard], data: {roles: ['ROLE_ADMIN', 'ROLE_GUEST']},
        children: [
          {path: '', component: CampaignComponent},
          {path: 'overview', component: CampaignCardOverviewComponent}
        ]
      },
      {path: '**', redirectTo: ''}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
