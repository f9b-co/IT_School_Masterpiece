import { HalfDay } from "./halfDay";
import { Task } from "./task";

export class Activity {

    date: String;
    halfDay: HalfDay;
    task: Task;

    constructor(date: String, halfDay: HalfDay, task: Task) {
        this.date = date;
        this.halfDay = halfDay;
        this.task = task;
    }

}