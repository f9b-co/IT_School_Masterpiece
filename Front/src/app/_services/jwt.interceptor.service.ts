import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { OauthTokenService } from './oauthToken.service';
import { AuthenticationService } from '../_services/authentication.service';

@Injectable({ providedIn: 'root' })

export class JwtInterceptorService implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService,
    private oauthTokenService: OauthTokenService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with access token if user is logged in and request is to api url
    const isLoggedIn = this.authenticationService.isCurrentUserLoggedIn();
    const isApiUrl = request.url.startsWith(`${environment.baseUrl}/api`);
    if (isLoggedIn && isApiUrl) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.oauthTokenService.getAccesToken()}`
        }
      });
    }

    return next.handle(request);
  }
}
