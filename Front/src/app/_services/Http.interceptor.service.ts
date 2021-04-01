import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";

import { environment } from '../../environments/environment';
import { AuthenticationService } from './authentication.service';
import { OauthTokenService } from "./oauthToken.service";

@Injectable({ providedIn: "root" })

export class HttpInterceptorService implements HttpInterceptor {

  constructor(private router: Router, private authenticationService: AuthenticationService,
    private oauthTokenService: OauthTokenService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    /*add auth header with access token if token is not expired, user is logged in and request is to api url*/
    const isLoggedIn = this.authenticationService.isCurrentUserLoggedIn();
    const isApiUrl = req.url.startsWith(`${environment.baseUrl}/api`);
    if (isLoggedIn && isApiUrl) {
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${this.oauthTokenService.getAccesToken()}` }
      });
    }
    return next.handle(req).pipe(catchError(this.handleError.bind(this)));
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = "Unknown error!";
    console.log("inside handle error" + JSON.stringify(error))
    if (error.error instanceof ErrorEvent) {
      /* Client-side errors */
      errorMessage = `Erreur locale: ${error.error.message}`;
    } else {
      /* Server-side errors */
      const msg = (error.error.error_description) ? error.error.error_description :
        (error.error) ? error.error : error.message;
      errorMessage = `Erreur serveur code ${error.status} : \n  ${msg}`;
      /* auto logout if 401 Unauthorized or 403 Forbidden response returned from api */
      if ([401, 403].indexOf(error.status) !== -1) {
        if (this.oauthTokenService.isTokenExpired()) {
          errorMessage = "session expirée!\nVeuillez vous reconnecter";
        }
        this.authenticationService.logout();
        this.router.navigate(['/login']);
      } else if (error.status == 0) {
        errorMessage = "Pas de réponse du serveur\nVeuillez réessayer dans quelques minutes";
      }
    }
    return throwError(errorMessage);
  }
}
