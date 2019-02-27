import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Postcard} from '../models/postcard';
import {Address} from '../models/address';
import {catchError} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {ToastUtilService} from './toast-util.service';
import {Cardhistory} from '../models/cardhistory';

const POSTCARD_API_PUBLIC = 'api/public/postcard';

@Injectable()
export class PostcardService {
  constructor(private http: HttpClient, private toastService: ToastUtilService) {
  }

  saveNewPostcard(postcard: Postcard): Observable<number> {
    return this.http.post<number>(`${POSTCARD_API_PUBLIC}/new`, postcard)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  approve(id: number): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/approve/${id}`, null)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  updateSender(id: number, adr: Address): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateSender/${id}`, adr)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  updateRecipient(id: number, adr: Address): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateRecipient/${id}`, adr)
      .pipe(
        catchError(
          this.toastService.handleError
        )
      );
  }

  updateText(id: number, text: string): Observable<{}> {
    const headers = new HttpHeaders().set('Content-Type', 'text/plain');
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateText/${id}`, text, {headers: headers})
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  updateFrontImage(id: number, imageId: number): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateFrontImage/${id}`, imageId)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }


  getCardHistory(id: number): Observable<Cardhistory[]> {
    return this.http.get<Cardhistory[]>(`${POSTCARD_API_PUBLIC}/getHistory/${id}`)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }


  getPreview(id: number, type: string): Observable<string> {
    return this.http.get<string>(`${POSTCARD_API_PUBLIC}/preview/${id}/${type}`)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

}
