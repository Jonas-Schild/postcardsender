import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {Location, registerLocaleData} from '@angular/common';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import localeDECH from '@angular/common/locales/de-CH';
import localeITCH from '@angular/common/locales/it-CH';
import localeFRCH from '@angular/common/locales/fr-CH';
import localeENGB from '@angular/common/locales/en-GB';

import {NgxDatatableModule} from '@swimlane/ngx-datatable';


import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HomeComponent} from './home/home.component';
import {CampaignService} from './services/campaign.service';
import {PostcardService} from './services/postcard.service';
import {StateService} from './services/state.service';
import {SecurityModule} from './security/security.module';
import {CampaignComponent} from './campaign/campaign.component';
import {PostcardComponent} from './postcard/postcard.component';
import {CampaignEditComponent} from './campaign/campaign-edit/campaign-edit.component';
import {CampaignReportComponent} from './campaign/campaign-report/campaign-report.component';
import {CampaignCardOverviewComponent} from './campaign/campaign-card-overview/campaign-card-overview.component';
import {ColorPickerModule} from 'ngx-color-picker';
import {AddressComponent} from './postcard/address/address.component';
import {NavigationBarComponent} from './campaign/navigation-bar/navigation-bar.component';
import {OverviewComponent} from './postcard/overview/overview.component';
import {ImageService} from './services/image.service';
import {ImagepickerComponent} from './common/imagepicker/imagepicker.component';
import {ImageuploaderComponent} from './common/imageuploader/imageuploader.component';
import {ToastUtilService} from './services/toast-util.service';
import {CampaignChooseComponent} from './campaign/campaign-choose/campaign-choose.component';
import {CardHistoryComponent} from './campaign/campaign-card-overview/card-history/card-history.component';
import {NgbDateParserFormatter, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from './common/dateformat';
import {ImageCropperModule} from 'ngx-image-cropper';


registerLocaleData(localeDECH, 'de');
registerLocaleData(localeITCH, 'it');
registerLocaleData(localeFRCH, 'fr');
registerLocaleData(localeENGB, 'en');


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'api/translations/messages/', '/messages.json');
}


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CampaignComponent,
    PostcardComponent,
    CampaignEditComponent,
    CampaignReportComponent,
    CampaignCardOverviewComponent,
    AddressComponent,
    NavigationBarComponent,
    OverviewComponent,
    ImagepickerComponent,
    ImageuploaderComponent,
    CampaignChooseComponent,
    CardHistoryComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      closeButton: true,
      timeOut: 1500,
      preventDuplicates: true,
    }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NgxDatatableModule,
    SecurityModule,
    ColorPickerModule,
    ImageCropperModule
  ],
  providers: [
    Location,
    CampaignService,
    PostcardService,
    StateService,
    ImageService,
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter},
    ToastUtilService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
