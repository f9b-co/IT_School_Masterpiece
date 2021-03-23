import { Employee } from "./employee";

export class Team {
    name: string;
    manager: Employee;

    constructor(name: string, manager: Employee) {
        this.name = name;
        this.manager = manager;
    }

}