import { Component, OnInit } from '@angular/core';

import { AuthenticationService } from '../_services/authentication.service';
import { ActivityService } from '../_services/activity.service';
import { User } from '../_models/user';
import { Role } from '../_models/role';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements OnInit {
  user: User = this.authenticationService.currentUserValue;
  isManager: boolean = this.user.roles.includes(Role.Manager);
  teams: any[] = [];
  today = new Date();
  actualYear = this.today.getFullYear();
  actualMonth = this.today.getMonth();
  monthOffset: number = 0;
  targetYear = this.actualYear;
  targetMonth = this.actualMonth;
  isTooLow: boolean = false;
  isTooHigh: boolean = false;
  yearMonth: string;
  daysInMonth: number;
  closedDaysInMonth: any = [];
  tableBody: HTMLElement = document.getElementById("activitiesTableBody");


  constructor(private authenticationService: AuthenticationService,
    private activityService: ActivityService) { }

  ngOnInit() {
    this.yearMonth = String(this.targetYear) + "-" + String(this.targetMonth + 1).padStart(2, '0');
    this.daysInMonth = this.findDaysInMonth(this.targetMonth, this.targetYear);
    this.closedDaysInMonth = this.activityService.listClosedDays(this.targetYear, this.targetMonth, this.daysInMonth);

    this.loadActivities(this.yearMonth);
    this.checkNavMonthButtons();
  }

  previousMonth() {
    if (this.targetMonth == 0) {
      this.targetYear--;
      this.targetMonth += 11;
    } else {
      this.targetMonth--;
    }
    this.monthOffset--
    this.checkMonthOffset();
    this.ngOnInit();
  }

  nextMonth() {
    if (this.targetMonth == 11) {
      this.targetYear++;
      this.targetMonth -= 11;
    } else {
      this.targetMonth++;
    }
    this.monthOffset++
    this.checkMonthOffset();
    this.ngOnInit();
  }

  checkMonthOffset() {
    this.isTooLow = this.monthOffset < -4 ? true : false;
    this.isTooHigh = this.monthOffset > 5 ? true : false;
  }

  checkNavMonthButtons() {
    const prev = document.getElementById("#previous");
    const next = document.getElementById("#next");
    (this.isTooLow) ? prev.style.visibility = "hidden" : prev.style.visibility = "visible";
    (this.isTooHigh) ? next.style.visibility = "hidden" : next.style.visibility = "visible";
    console.log(this.isTooLow + " " + this.isTooHigh)
  }

  loadTeamsMembers() {

  }

  loadActivities(yearMonth: string) {

    // Send http request with form values to back api
    this.activityService.getMonthListedActivities(yearMonth, this.user.username, this.isManager).subscribe(
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

      for (let i = 0; i < this.daysInMonth; i++) {
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

  findDaysInMonth(month, year) {
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
