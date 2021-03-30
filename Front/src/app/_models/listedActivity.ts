import { Activity } from "./activity";

export class ListedActivity {

    activity: Activity;
    isValidated: boolean;
    employeeId: number;

    constructor(activity: Activity, isValidated: boolean, employeeId?: number) {
        this.activity = activity;
        this.isValidated = isValidated;
        this.employeeId = employeeId;
    }

}