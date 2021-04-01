import { Activity } from "./activity";

export class ListedActivity {

    activity: Activity;
    validated: boolean;
    employeeId: number;

    constructor(activity: Activity, validated: boolean, employeeId?: number) {
        this.activity = activity;
        this.validated = validated;
        this.employeeId = employeeId;
    }

}