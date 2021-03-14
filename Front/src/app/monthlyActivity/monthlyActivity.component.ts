import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { ActivityService } from '../_services/activity.service';
import { User } from '../_models/user';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements OnInit {
  targetMonthOffset: number = 4;
  daysInTargetMonth: number;
  closedDaysInMonth: number[] = [];
  tableBody: HTMLElement = document.getElementById("activitiesTableBody");

  constructor(private http: HttpClient) { }

  ngOnInit() {
    const today = new Date();
    const targetYear = today.getFullYear();
    const targetMonth = today.getMonth() + this.targetMonthOffset;
    const daysInTargetMonth = this.daysInMonth(targetMonth, targetYear);
    const closedDaysInMonth = this.listClosedDays(targetYear, targetMonth, daysInTargetMonth);

  }

  loadActivities(url) {
    fetch(url)
      .then((resp) => resp.json())
      .then((jsonResp) => this.tableize(jsonResp))
      .catch((error) => alert("Erreur : " + error));
  }

  tableize(listedActivities) {
    console.log(listedActivities);


    listedActivities.forEach((country) => {
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

  listClosedDays(year: number, month: number, daysInMonth: number) {
    this.http.get<any>('../../assets/jsons/api-gouv-fr_jours-feries_2020-2022.json')
      .subscribe((res: any) => {
        const publicHoliday = [];
        for (let date in res) {
          const monthDay = new Date(date)
          if (monthDay.getFullYear() == year && monthDay.getMonth() == month) {
            publicHoliday.push(monthDay.getDate());
          }
        }
        console.log(year + "-" + month + " publicHoliday = " + publicHoliday)
        const closedDays = [];
        for (let i = 1; i <= daysInMonth; i++) {
          const weekDay = new Date(year, month, i).getDay();
          if (weekDay == 0 || weekDay == 6 || publicHoliday.includes(i)) {
            closedDays.push(i)
          }
        }
        console.log(closedDays);
        return closedDays;
      });
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
