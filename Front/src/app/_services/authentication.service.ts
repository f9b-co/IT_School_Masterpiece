import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';

import { environment } from '../../environments/environment';
import { User } from '../_models/user';
import { JwtService } from '../_services/jwt.service';


@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  private user: User = this.jwtService.getUserFromToken() || new User("guest");
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private jwtService: JwtService) {
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
          user = this.jwtService.getUserFromToken();
          this.currentUserSubject.next(user);
        }
        console.log(user);
        console.log("Connexion réussie");
        return user;
      }));
  }

  logout(): void {
    // remove token from session storage to log user out
    window.sessionStorage.removeItem('token');
    this.currentUserSubject.next(null);
    console.log("Déconnexion réussie");
  }

  isCurrentUserLoggedIn(): boolean {
    return ((this.currentUserValue) && !isNullOrUndefined(this.currentUserValue.roles)) ? true : false;
  }

}
