import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { User } from '../_models/user';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements OnInit {
  daysInTargetMonth: number;
  targetMonthOffset : number = 0;
  tableBody: HTMLElement = document.getElementById("activitiesTableBody");

  constructor() { }

  ngOnInit() {
    const today = new Date();
    const targetYear = today.getFullYear();
    const targetMonth = today.getMonth() + 1 + this.targetMonthOffset;
    const daysInTargetMonth = this.daysInMonth (targetMonth, targetYear);
    console.log(today+"-"+targetYear+"-"+targetMonth+"-"+daysInTargetMonth)

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
  
  daysInMonth (month, year) {
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
