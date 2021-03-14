import { Component, OnInit } from '@angular/core';

import { AuthenticationService } from '../_services/authentication.service';
import { ActivityService } from '../_services/activity.service';
import { User } from '../_models/user';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements OnInit {
  user: User = this.authenticationService.currentUserValue;
  teams: any[] = [];
  targetMonthOffset: number = 0;
  daysInTargetMonth: number;
  closedDaysInMonth: number[] = [];
  tableBody: HTMLElement = document.getElementById("activitiesTableBody");


  constructor(private authenticationService: AuthenticationService,
    private activityService: ActivityService) { }

  ngOnInit() {
    const today = new Date();
    const targetYear = today.getFullYear();
    const targetMonth = today.getMonth() + this.targetMonthOffset;
    const daysInTargetMonth = this.daysInMonth(targetMonth, targetYear);
    const closedDaysInMonth = this.activityService.listClosedDays(targetYear, targetMonth, daysInTargetMonth);

    this.loadActivities();
  }

  loadActivities() {

    // Send http request with form values to back api
    this.activityService.getMonthListedActivities(this.targetMonthOffset, this.user.username).subscribe(
      (data) => {
        console.log(data);
        //this.tableize(data);
      },
      (error) => {
        alert(error);
      }
    );
  }

  tableize(listedActivities) {
    console.log(listedActivities);


    listedActivities.forEach((userActivities) => {
      const tr = this.createNode("tr");

      for (let i = 0; i < this.daysInTargetMonth; i++) {
        const el = "";
        const td = this.createNode("td");
        if (i != 0) {
          td.setAttribute("class", "");
        }
        if (i == 0) {
          td.setAttribute("class", "");
        }
        td.innerHTML = el;
        this.append(tr, td);
      }

      this.append(this.tableBody, tr);
    });
  }

  daysInMonth(month, year) {
    return new Date(year, month, 0).getDate();
  }



  notNullCheck(el) {
    if (el != null) {
      return el;
    } else return "N/A";
  }

  append(parent, el) {
    return parent.appendChild(el);
  }

  createNode(el) {
    return document.createElement(el);
  }

}
