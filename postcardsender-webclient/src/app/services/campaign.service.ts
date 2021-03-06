import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Campaign} from '../models/campaign';
import {DataSizeAndData} from '../models/data-size-and-data';
import {Postcard} from '../models/postcard';
import {ToastUtilService} from './toast-util.service';
import {catchError, map} from 'rxjs/operators';
import {CampaignStatistic} from '../models/campaign-statistic';
import {CardSearch} from '../models/card-search';

const CAMP_API_PUBLIC = 'api/public/campaign';
const CAMP_API_PROTECTED = 'api/protected/campaign';

@Injectable()
export class CampaignService {
  constructor(private http: HttpClient, private toastService: ToastUtilService) {
  }

  /**
   * Returns all active campaigns
   */
  getAllCurrentcampaigns(): Observable<Campaign[]> {
    return this.http.get<Campaign[]>(`${CAMP_API_PUBLIC}/currentActive`).pipe(
      catchError(err => {
          return this.toastService.handleError(err);
        }
      )
    );
  }

  /**
   * Returns all  campaigns
   */
  getAllCampaigns(): Observable<Campaign[]> {
    return this.http.get<Campaign[]>(`${CAMP_API_PROTECTED}/all`).pipe(
      catchError(err => {
          return this.toastService.handleError(err);
        }
      )
    );
  }

  /**
   * Get a campaign with the key
   */
  getCampaignByKey(key: string): Observable<Campaign> {
    return this.http.get<Campaign>(`${CAMP_API_PUBLIC}/key/${key}`).pipe(
      catchError(err => {
          return this.toastService.handleError(err);
        }
      )
    );
  }


  /**
   * Returns all defined front-images to a campaign-id
   */
  getImages(id: number): Observable<number[]> {
    return this.http.get<number[]>(`${CAMP_API_PUBLIC}/images/${id}`).pipe(
      catchError(err => {
          return this.toastService.handleError(err);
        }
      )
    );
  }

  /**
   * Returns the requested page with created postcards matching the search-criteria to a campaign-id
   */
  getPostcardsForCampaign(campId: number, page: number, search: CardSearch): Observable<DataSizeAndData<Postcard>> {
    return this.http
      .post<DataSizeAndData<Postcard>>(
        `${CAMP_API_PROTECTED}/searchPostcards/${campId}/${page}`, search).pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Returns an excel-file with created postcards matching the search-criteria to a campaign-id
   */
  downloadPostcardReport(campId: number, search: CardSearch): Observable<Blob> {
    return this.http.post(`${CAMP_API_PROTECTED}/generateExport/${campId}`, search, {responseType: 'arraybuffer'}).pipe(
      map(response => new Blob([response], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'})));
  }

  /**
   * Returns statistics for a campaign-id
   */
  getCampaignStatistic(campId: number): Observable<CampaignStatistic> {
    return this.http
      .get<CampaignStatistic>(
        `${CAMP_API_PROTECTED}/statistic/${campId}`);
  }

  /**
   * Save a campaign and returns the generated id
   */
  saveCampaign(camp: Campaign): Observable<number> {
    return this.http.post<number>(`${CAMP_API_PROTECTED}/save`, camp)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Remove an image from a campaign
   */
  removeImage(campId: number, imgId: number): Observable<{}> {
    return this.http.delete(`${CAMP_API_PROTECTED}/removeImage/${campId}/${imgId}`)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Add image to campaign
   */
  addImage(campId: number, imgId: number): Observable<{}> {
    return this.http.put(`${CAMP_API_PROTECTED}/addImage/${campId}/${imgId}`, null)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Add stamp to campaign
   */
  addStamp(campId: number, imgId: number): Observable<{}> {
    return this.http.put(`${CAMP_API_PROTECTED}/addStamp/${campId}/${imgId}`, null)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

  /**
   * Remove stamp from campaign
   */
  removeStamp(campId: number): Observable<{}> {
    return this.http.delete(`${CAMP_API_PROTECTED}/removeStamp/${campId}`)
      .pipe(
        catchError(err => {
            return this.toastService.handleError(err);
          }
        )
      );
  }

}
