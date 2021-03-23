import { HalfDay } from "./halfday";
import { Task } from "./task";

export class Activity {

    date: String;
    halfday: HalfDay;
    task: Task;

    constructor(date: String, halfday: HalfDay, task: Task) {
        this.date = date;
        this.halfday = halfday;
        this.task = task;
    }

}