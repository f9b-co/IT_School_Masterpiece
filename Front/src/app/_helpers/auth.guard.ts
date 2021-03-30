import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { AuthenticationService } from '../_services/authentication.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(private router: Router, private authenticationService: AuthenticationService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.authenticationService.currentUserValue;
        const islogged = this.authenticationService.isCurrentUserLoggedIn()
        if (islogged) {
            // check if route is restricted by role
            if (route.data.roles && !route.data.roles.some(role => currentUser.roles.includes(role))) {
                // role not authorised so don't activate routing
                alert('Vous n\'avez pas les droits pour afficher le contenu!');
                return false;
            }
            // authorised so activate routing
            return true;
        }
        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login']);
        return false;
    }
    
}