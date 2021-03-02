import { Role } from './role';

export class User {
  username: string;
  userId: number;
  roles: Role[];

  constructor(username: string, userId?: number, authorities?: Role[]) {
    this.username = username;
    this.userId = userId;
    this.roles = authorities;
  }

}
