import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';

import { environment } from '../../environments/environment';
import { User } from '../_models/user';
import { OauthTokenService } from './oauthToken.service';


@Injectable({ providedIn: 'root' })

export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  private user: User = this.OauthTokenService.getUserFromToken() || new User("guest");
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
    const headers = new HttpHeaders().set(
      "Content-Type",
      "application/x-www-form-urlencoded"
    );
    return this.http.post<any>(url, requiredAuthData, { headers })
      .pipe(map(response => {
        let user = this.currentUserSubject.value;
        // login successful if the response is a token containing an access token
        if (response.access_token) {
          // store token in session storage to keep user logged in between page refreshes
          window.sessionStorage.setItem('token', JSON.stringify(response));
          user = this.OauthTokenService.getUserFromToken();
          this.currentUserSubject.next(user);
          console.log("Connexion réussie");
        } /* else {
          alert("erreur de connexion\n" + response.error)
        } */
        return user;
      }));
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
