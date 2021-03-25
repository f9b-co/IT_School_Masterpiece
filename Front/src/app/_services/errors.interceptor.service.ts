import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";

import { AuthenticationService } from '../_services/authentication.service';

@Injectable({ providedIn: "root" })

export class ErrorsInterceptorService implements HttpInterceptor {

  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  handleError(error: HttpErrorResponse) {
    let errorMessage = "Unknown error!";
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      // Server-side errors
      errorMessage = `Server Error Code: ${error.status}\nMessage: ${error.message}`;
      // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
      if ([401, 403].indexOf(error.status) !== -1) {
        /*         this.authenticationService.logout();
                this.router.navigate(['/login']); */
      }
    }
    //window.alert(errorMessage);
    return throwError(errorMessage);
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(this.handleError));
  }
}
