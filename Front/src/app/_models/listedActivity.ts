import { Activity } from "./activity";

export class ListedActivity {

    activity: Activity;
    isValidated: boolean;

    constructor(activity: Activity, isValidated: boolean) {
        this.activity = activity;
        this.isValidated = isValidated;
    }

}