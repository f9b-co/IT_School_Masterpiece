import { Department } from "./department";
import { ListedActivity } from "./listedActivity";
import { Team } from "./team";

export class Employee {
  username: string;
  firstName: String;
  lastName: String;
  team: Team;
  listedActivities: ListedActivity[];
  email: String;
  department: Department;

  constructor(username: string, firstName: String, lastName: String, team: Team,
    listedActivities?: ListedActivity[], email?: String, department?: Department) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.team = team;
    this.listedActivities = listedActivities;
    this.department = department;
    this.email = email;
  }

}