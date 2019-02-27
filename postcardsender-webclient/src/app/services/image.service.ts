import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

const IMAGE = 'api/public/image';

@Injectable()
export class ImageService {

  constructor(private http: HttpClient) {
  }

  // public getImageFile(id: number): Observable<Blob> {
  //   return this.http
  //     .get(`${IMAGE}/${id}`, {observe: 'response', responseType: 'blob'})
  //     .map((res: any) => res._body)
  //     .catch(error => this.errorService.handleError(error));
  // }

  public getImageFile(id: number): Observable<Blob> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });
    return this.http.get<Blob>(`${IMAGE}/${id}`, {headers: headers, responseType: 'blob' as 'json'});
  }


  // public getImageFile(id: number): Observable<Blob> {
  //   return this.http.get<Blob>(`${IMAGE}/${id}`, {responseType: 'blob' as 'json'});
  // }

  saveImage(file: File): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', `${IMAGE}/post`, formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }

}


