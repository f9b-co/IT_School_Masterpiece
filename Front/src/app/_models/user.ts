import { Role } from './role';

export class User {
  username: string;
  userId: number;
  roles: Role[];
  firstName: string;
  lastName: string;
  teamName: string;

  constructor(username: string, userId?: number, authorities?: Role[],
    firstName?: string, lastName?: string, teamName?: string) {
    this.username = username;
    this.userId = userId;
    this.roles = authorities;
    this.firstName = firstName;
    this.lastName = lastName;
    this.teamName = teamName;
  }

}
