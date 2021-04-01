import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

import { Token } from '../_models/token';
import { User } from '../_models/user';

@Injectable({
  providedIn: 'root'
})
export class OauthTokenService {
  private decoded: { [key: string]: any };

  constructor() { }

  storeToken(token: Token) {
    window.sessionStorage.setItem('token', JSON.stringify(token));
  }

  haveToken(): boolean {
    return (sessionStorage.getItem('token')) ? true : false;
  }

  getAccesToken() {
    if (this.haveToken()) {
    return JSON.parse(sessionStorage.getItem('token')).access_token
    }
  }

  getDecodedAccesToken() {
    if (this.haveToken()) {
      this.decoded = jwt_decode(this.getAccesToken());
      return this.decoded
    }
  }

  getUserFromStoredToken() {
    if (this.haveToken()) {
      this.decoded = this.getDecodedAccesToken();
      return new User(this.decoded.user_name, this.decoded.userId, this.decoded.authorities);
    }
  }

  getUserFromToken(token: Token) {
    this.decoded = jwt_decode(token.access_token);
    return new User(this.decoded.user_name, this.decoded.userId, this.decoded.authorities);
  }

  isTokenExpired(): boolean {
    if (this.haveToken()) {
      const expiry = this.getDecodedAccesToken().exp;
      return (this.haveToken()) ? (Math.floor((new Date).getTime() / 1000)) >= expiry : true;
    }
  }
}
