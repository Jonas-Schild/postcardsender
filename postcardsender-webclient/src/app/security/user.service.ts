/* reused and adapted from Post CH*/
import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable, of, Subject} from 'rxjs';
import {User} from './user.interface';

@Injectable()
export class UserService {

  private user: User;

  constructor(private http: HttpClient, private router: Router) {
  }

  private getCurrentUserFromServer() {
    console.log('Loading user from server.');
    return this.http.get('api/users/current');
  }

  login(username, password) {
    console.log('Login call');

    // logout first
    this.logout();

    // login call
    const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    const loginCallPromise = this.http
      .post('auth/login', body, {headers: headers, responseType: 'text'});

    loginCallPromise.subscribe(
      // Success: navigate to campaign
      (data) => this.router.navigate(['/ui/campaign']),
      // Error: navigate to login
      (response: HttpErrorResponse) => {
        console.log(`Error on login: ${response.status} - ${response.statusText}`);
        this.redirectToLogin(response.status, response.statusText);
      }
    );
  }

  logout() {
    this.user = null;
    this.http
      .post('auth/logout', null, {responseType: 'text'}).subscribe();
  }

  isLoggedIn(): boolean {
    return !!this.user;
  }

  getCurrentUser(): User {
    return this.user;
  }

  canActivate(neededRoles: Array<string>): Observable<boolean> {
    if (this.isLoggedIn()) {
      console.log('User is already logged in');

      if (this.hasOneOfNeededRoles(this.user.roles, neededRoles)) {
        return of(true);
      } else {
        console.log(`User is missing one of needed roles ${neededRoles}`);
        this.redirectToLogin(401, 'Unauthorized');
      }
    } else {
      console.log('We don\'t know if the user is logged in, we get him from the server');

      const canActivateSubject = new Subject<boolean>();

      this.getCurrentUserFromServer()
        .subscribe((user: User) => {

          console.log('User loaded: ', user);
          this.user = user;

          if (this.hasOneOfNeededRoles(this.user.roles, neededRoles)) {
            canActivateSubject.next(true);
          } else {
            console.log(`User is missing one of needed roles ${neededRoles}`);
            this.redirectToLogin(401, 'Unauthorized');
            canActivateSubject.next(false);
          }
          canActivateSubject.complete();

        }, (error) => {

          console.log(`Error loading user: ${error.status} - ${error.statusText}`);
          this.redirectToLogin(error.status, error.statusText);

          canActivateSubject.next(false);
          canActivateSubject.complete();
        });

      return canActivateSubject;
    }
  }

  private redirectToLogin(status: number, statusText: string) {
    this.router.navigate(['/login'], {
      queryParams: {
        status: status,
        statusText: statusText
      }
    });
  }

  private hasOneOfNeededRoles(userRoles: string[], neededRoles: Array<string>) {
    let hasOneOfNeededRoles = false;
    userRoles.forEach(role => {
      if (neededRoles.indexOf(role) > -1) {
        hasOneOfNeededRoles = true;
      }
    });
    return hasOneOfNeededRoles;
  }
}
