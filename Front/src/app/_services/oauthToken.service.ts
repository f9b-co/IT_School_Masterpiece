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

  storeToken(token: Token): void {
    window.sessionStorage.setItem('token', JSON.stringify(token));
  }

  private haveToken(): boolean {
    return (sessionStorage.getItem('token')) ? true : false;
  }

  private getStoredToken(): Token {
    if (this.haveToken()) {
      return JSON.parse(sessionStorage.getItem('token'))
    }
  }

  getAccesToken(): string {
    if (this.haveToken()) {
      return this.getStoredToken().access_token
    }
  }

  private getDecodedAccesToken(): { [key: string]: any } {
    if (this.haveToken()) {
      this.decoded = jwt_decode(this.getAccesToken());
      return this.decoded
    }
  }

  getUserFromStoredToken(): User {
    if (this.haveToken()) {
      this.decoded = this.getDecodedAccesToken();
      return new User(this.decoded.user_name, this.decoded.userId, this.decoded.authorities);
    }
  }

  getUserFromToken(token: Token): User {
    this.decoded = jwt_decode(token.access_token);
    return new User(this.decoded.user_name, this.decoded.userId, this.decoded.authorities);
  }

  getTokenAge(): number {
    if (this.haveToken()) {
      const expiry = this.getDecodedAccesToken().exp;
      return this.getStoredToken().expires_in - (expiry - (Math.floor((new Date).getTime() / 1000)));
    }
  }

  isTokenExpired(): boolean {
    const expiry = (this.haveToken()) ? this.getDecodedAccesToken().exp : 0;
    return (this.haveToken()) ? (Math.floor((new Date).getTime() / 1000)) >= expiry : true;
  }
}
