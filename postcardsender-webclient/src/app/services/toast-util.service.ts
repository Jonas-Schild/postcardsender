import {ToastrService} from 'ngx-toastr';
import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';

@Injectable()
export class ToastUtilService {

// constructors ///////////////////////////////////////////////////////////////

  constructor(private toastr: ToastrService, public translateService: TranslateService) {
  }

// methods ////////////////////////////////////////////////////////////////////

  public handleError(error: HttpErrorResponse) {
    console.log(error);
    let msg = error.statusText;
    if (error.error != null) {
      msg = error.error.message;
    }
    this.toastSystemError(msg, 'An error occurred:');

    // return an observable
    return throwError(
      error.error);
  }

  public handleErrorWithCustomText(error: HttpErrorResponse, title: string, text: string) {
    this.toastError(title, text);
    // return an observable
    return throwError(
      error.error);
  }


  public toastSuccess(titleCode, messageCode) {
    const title = this.translateService.instant(titleCode);
    let message = '';
    if (messageCode) {
      message = this.translateService.instant(messageCode);
    }
    console.log('toastSuccess' + message);
    this.toastr.success(message, title);
  }

  public toastInfo(titleCode, messageCode) {
    const title = this.translateService.instant(titleCode);
    let message = '';
    if (messageCode) {
      message = this.translateService.instant(messageCode);
    }
    this.toastr.info(message, title);
  }

  public toastError(titleCode, messageCode) {
    const title = this.translateService.instant(titleCode);
    let message = '';
    if (messageCode) {
      message = this.translateService.instant(messageCode);
    }
    this.toastr.error(message, title);
  }

  public toastSystemError(message: string, title: string) {
    this.toastr.error(message, title);
  }


}
