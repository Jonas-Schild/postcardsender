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

  /**
   * Save a new postcard, returns the generated id
   */
  saveNewPostcard(postcard: Postcard): Observable<number> {
    return this.http.post<number>(`${POSTCARD_API_PUBLIC}/new`, postcard)
      .pipe(
        catchError(err => {
            // 429 too many requests
            if (err.status === 429) {
              return this.toastService.handleErrorWithCustomText(err, 'Limit erreicht',
                'Heute können Sie keine Postkarten mehr versenden.');
            }
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Approve a postcard
   */
  approve(id: number): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/approve/${id}`, null)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Update the sender address at a postcard (with postcard-id)
   */
  updateSender(id: number, adr: Address): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateSender/${id}`, adr)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Update the recipient address at a postcard (with postcard-id)
   */
  updateRecipient(id: number, adr: Address): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateRecipient/${id}`, adr)
      .pipe(
        catchError(
          this.toastService.handleError
        )
      );
  }

  /**
   * Update the text at a postcard (with postcard-id)
   */
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

  /**
   * Update frontimage at a postcard (with postcard-id)
   */
  updateFrontImage(id: number, imageId: number): Observable<{}> {
    return this.http.put(`${POSTCARD_API_PUBLIC}/updateFrontImage/${id}`, imageId)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Returns the history to a postcard
   */
  getCardHistory(id: number): Observable<Cardhistory[]> {
    return this.http.get<Cardhistory[]>(`${POSTCARD_API_PUBLIC}/getHistory/${id}`)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Returns the preview of a postcard over the API
   */
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
