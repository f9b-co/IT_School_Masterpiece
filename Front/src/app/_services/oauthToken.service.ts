import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

import { User } from '../_models/user';

@Injectable({
  providedIn: 'root'
})
export class OauthTokenService {
  private decoded: { [key: string]: any };

  constructor() { }

  getAccesToken() {
    return JSON.parse(sessionStorage.getItem('token')).access_token
  }

  getDecodedAccesToken() {
    this.decoded = jwt_decode(this.getAccesToken());
    return this.decoded
  }

  haveToken(): boolean {
    return (sessionStorage.getItem('token')) ? true : false;
  }

  getUserFromToken() {
    if (this.haveToken()) {
      this.decoded = this.getDecodedAccesToken();
      return new User(this.decoded.user_name, this.decoded.userId, this.decoded.authorities);
    }
  }

  isTokenExpired(): boolean {
    const expiry = this.getDecodedAccesToken().exp;
    return (this.haveToken()) ? (Math.floor((new Date).getTime() / 1000)) >= expiry : true;
  }
}