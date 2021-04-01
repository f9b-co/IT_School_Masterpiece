import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, concatMap, mergeMap } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';

import { environment } from '../../environments/environment';
import { User } from '../_models/user';
import { OauthTokenService } from './oauthToken.service';


@Injectable({ providedIn: 'root' })

export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  private user: User = this.OauthTokenService.getUserFromStoredToken() || new User("guest");
  public currentUser: Observable<User>;

  constructor(private router: Router, private http: HttpClient, private OauthTokenService: OauthTokenService) {
    this.currentUserSubject = new BehaviorSubject<User>(this.user);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(requiredAuthData): any {
    const url = `${environment.baseUrl}/oauth/token`;
    const authHeaders = new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded");

    /* chain & merge 2 http request, 1st for Auth, 2nd to enrich user with first name, last name and team name */
    return this.http.post<any>(url, requiredAuthData, { headers: authHeaders })
      .pipe(mergeMap(token => this.http.get<any>(
        `${environment.apiUrl}/employees/${this.OauthTokenService.getUserFromToken(token).username}`,
        { headers: { "Content-Type": "application/json", "Authorization": "Bearer" + token.access_token } })
        .pipe(map(shortEmployee => {
          let user = this.currentUserSubject.value;
          /* login successful if the response for 1st http request is a token containing an access token */
          if (token.access_token) {
            /* store token in session storage to keep user logged in between page refreshes
            and complete user with properties of shortEmployee form the 2nd http request */
            this.OauthTokenService.storeToken(token);
            user = this.OauthTokenService.getUserFromToken(token);
            user.firstName = shortEmployee.firstName;
            user.lastName = shortEmployee.lastName;
            user.teamName = shortEmployee.teamName;
            this.currentUserSubject.next(user);
            console.log("Connexion réussie");
          }
          return user;
        }))
      ));
  }

  logout(): void {
    // remove token from session storage to log user out
    window.sessionStorage.removeItem('token');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
    console.log("Déconnexion réussie");
  }

  isCurrentUserLoggedIn(): boolean {
    return ((this.currentUserValue) && !isNullOrUndefined(this.currentUserValue.roles)) ? true : false;
  }

}
