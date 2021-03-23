import { Component, AfterViewInit, ElementRef, ViewChild, Renderer2, OnInit } from '@angular/core';
import { AuthenticationService } from '../_services/authentication.service';
import { ActivityService } from '../_services/activity.service';
import { isNullOrUndefined } from 'util';

import { User } from '../_models/user';
import { Role } from '../_models/role';
import { Department } from '../_models/department';
import { Team } from '../_models/team';
import { Task } from '../_models/task';
import { Activity } from '../_models/activity';
import { ListedActivity } from '../_models/listedActivity';
import { Employee } from '../_models/employee';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements AfterViewInit, OnInit {
  user: User = this.authenticationService.currentUserValue;
  isManager: boolean = this.user.roles.includes(Role.Manager);
  teams: string[] = [];
  tmpTeam: string = "";
  teamsMembers: Employee[] = [];
  today: Date = new Date();
  presentYear: number = this.today.getFullYear();
  presentMonth: number = this.today.getMonth();
  monthChange: number = 0;
  monthOffset: number = 0;
  targetYear: number = this.presentYear;
  targetMonth: number = this.presentMonth;
  isTooLow: boolean = false;
  isTooHigh: boolean = false;
  yearMonth: string = this.yearMonthToString(this.targetYear, this.targetMonth);
  daysInMonth: number;
  closedDaysInMonth: any = [];

  constructor(private authenticationService: AuthenticationService,
    private activityService: ActivityService, private renderer: Renderer2) { }

  @ViewChild('activitiesTableHead', { static: false }) tableHead: ElementRef;
  @ViewChild('activitiesTableBody', { static: false }) tableBody: ElementRef;

  ngOnInit() {
    //this.loadTeamsInfos(this.user.userId, this.user.username, this.isManager)

  }

  ngAfterViewInit() {
    this.tableHead.nativeElement.innerHTML = "";
    this.tableBody.nativeElement.innerHTML = "";

    this.yearMonth = this.yearMonthToString(this.targetYear, this.targetMonth)
    this.daysInMonth = this.findDaysInMonth(this.targetMonth + 1, this.targetYear);
    this.closedDaysInMonth = this.activityService.listClosedDays(this.targetYear, this.targetMonth, this.daysInMonth);
    this.buildActivityTableHead();

    this.loadMonthlyActivity(this.user.userId, this.user.username, this.isManager);
    this.checkNavMonthButtons();
  }

  yearMonthToString(year: number, month: number): string {
    return String(this.targetYear) + "-" + String(this.targetMonth + 1).padStart(2, '0');
  }
  findDaysInMonth(year: number, month: number): number {
    return new Date(year, month, 0).getDate();
  }
  checkMonthOffset(): void {
    this.isTooLow = this.monthOffset < -4 ? true : false;
    this.isTooHigh = this.monthOffset > 5 ? true : false;
  }
  previousMonth(): void {
    if (this.targetMonth == 0) {
      this.targetYear--;
      this.targetMonth += 11;
    } else {
      this.targetMonth--;
    }
    this.monthOffset--;
    this.monthChange++;
    this.checkMonthOffset();
    this.ngAfterViewInit();
  }
  nextMonth(): void {
    if (this.targetMonth == 11) {
      this.targetYear++;
      this.targetMonth -= 11;
    } else {
      this.targetMonth++;
    }
    this.monthOffset++;
    this.monthChange++;
    this.checkMonthOffset();
    this.ngAfterViewInit();
  }
  checkNavMonthButtons(): void {
    const prev = document.getElementById("previous");
    const next = document.getElementById("next");
    (this.isTooLow) ? prev.style.visibility = "hidden" : prev.style.visibility = "visible";
    (this.isTooHigh) ? next.style.visibility = "hidden" : next.style.visibility = "visible";
  }
  buildActivityTableHead(): void {
    /* define local variables to store most used needed instance members
    code readability and  maintainability improved,
    with shorten code and very limited repetition of this,
    and with direct context access to definition of used instance members */
    const render = this.renderer;
    const tH = this.tableHead;
    const crEl = this.createElement;
    const apd = this.append;

    let newCol, newRow, newHeader;
    /* Define columms witdth via html5 <col> */
    newCol = crEl(render, "col", "", "", "", "width:15%")
    apd(render, tH.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.85 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "", "", "", "width:" + width + "%");
      apd(render, tH.nativeElement, newCol);
    }
    /* Add space blank row */
    newRow = crEl(render, "tr", "firstBlankRow", "");
    apd(render, tH.nativeElement, newRow);
    /* Add colums'headers row */
    newRow = crEl(render, "tr", this.yearMonth, "");
    newHeader = crEl(render, "th", "nomPrenom", "", "Nom")
    apd(render, newRow, newHeader)
    for (let i = 0; i < this.daysInMonth; i++) {
      const day = String(i + 1).padStart(2, '0');
      newHeader = crEl(render, "th", this.yearMonth + "-" + day, "", day);
      apd(render, newRow, newHeader);
    }
    (this.isManager) ?
      newHeader = crEl(render, "th", "checkActivity", "", "&#10003") :
      newHeader = crEl(render, "th", "checkActivity", "hidden", "&#10003");
    apd(render, newRow, newHeader);
    apd(render, tH.nativeElement, newRow);

  }
  loadMonthlyActivity(userId: number, username: string, isManager: boolean) {
    if (isManager) {
      this.activityService.getManagerTeamsMembers(userId).subscribe(
        (managerTeamsMembers: Employee[][]) => {
          console.log(managerTeamsMembers);
          if (this.monthChange == 0) {
            managerTeamsMembers.forEach(tm => {
              (isNullOrUndefined(tm[0].team)) ?
                this.teams.push("") :
                this.teams.push(tm[0].team.name);
              console.log(this.teams.length);
              tm.forEach(member => this.teamsMembers.push(member));
            });
          }
          this.loadActivities(this.yearMonth, username)
        },
        (error) => { alert(error); }
      );
    } else {
      this.activityService.getUserTeamMembers(username).subscribe(
        (userTeamMembers: Employee[]) => {
          console.log(userTeamMembers);
          if (this.monthChange == 0) {
            (isNullOrUndefined(userTeamMembers[0].team)) ?
              this.teams.push("") :
              this.teams.push(userTeamMembers[0].team.name);
            console.log(this.teams.length);
            userTeamMembers.forEach(member => this.teamsMembers.push(member));
          }
          this.loadActivities(this.yearMonth, username)
        },
        (error) => { alert(error); }
      );
    }
  }
  loadActivities(yearMonth: string, username: string) {
    if (this.teams.length > 0) {
      this.teams.forEach(t => {
        this.getTeamsActivities(yearMonth, t, username);
      });
    } else {
      alert("pas d'employés à afficher!")
    }
  }
  getTeamsActivities(yearMonth: string, teamName: string, username: string): any {
    this.activityService.getMonthListedActivities(yearMonth, teamName, username).subscribe(
      (teamActivities: Employee[]) => {
        console.log(teamActivities);
        if (isNullOrUndefined(teamActivities[0])) {
          const substitute = this.teamsMembers
            .filter(m => m.team.name == teamName)
            .map(m => new Employee(m.username, m.firstName, m.lastName, m.team, new Array(0)));
          console.log(substitute)
          this.tableize(teamName, substitute);
        } else {
          this.tableize(teamName, teamActivities);
        }
      },
      (error) => {
        alert(error);
      }
    );
  }
  tableize(teamName: string, teamActivities: any[]): void {
    console.log(teamName);
    console.log(teamActivities);
    const render = this.renderer;
    const tB = this.tableBody;
    const crEl = this.createElement;
    const apd = this.append;

    let newCol, newRow, newCell, newDiv;

    newCol = crEl(render, "col", "colNom", "", "", "width:15%")
    apd(render, tB.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.85 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "col" + (i + 1), "", "", "width:" + width + "%");
      apd(render, tB.nativeElement, newCol);
    }

    newRow = crEl(render, "tr", teamName + "-titleRow");
    newCell = crEl(render, "th", teamName + "-titleCell", "", teamName)
    apd(render, newRow, newCell)
    apd(render, tB.nativeElement, newRow);

    teamActivities.forEach(member => {
      const ListedActivities: ListedActivity[] = member.listedActivities;
      const dayActivities = ListedActivities.map(la => la.activity).filter(a => a.date == "2021-03-05");
      console.log(ListedActivities);
      console.log(dayActivities);
      newRow = crEl(render, "tr", member.username, "nom-prenom");
      newCell = crEl(render, "td", "nomPrenom", "", member.lastName + "," + member.firstName)
      apd(render, newRow, newCell)
      for (let i = 0; i < this.daysInMonth; i++) {
        const day = String(i + 1).padStart(2, '0');
        newCell = crEl(render, "td", member.username + "-" + day, "days-tr");
        newDiv = crEl(render, "div", member.username + "-" + day + "-" + "AM", "days-tr-div-AM");
        apd(render, newCell, newDiv);
        newDiv = crEl(render, "div", member.username + "-" + day + "-" + "PM", "days-tr-div-PM");
        apd(render, newCell, newDiv);
        apd(render, newRow, newCell);
      }
      (this.isManager) ?
        newCell = crEl(render, "td", member.username + "-" + this.yearMonth, "", "") :

        newCell = crEl(render, "td", member.username + "-" + this.yearMonth, "hidden", "[]");
      apd(render, newRow, newCell);
      apd(render, tB.nativeElement, newRow);

    });


  }

  append(renderer: Renderer2, parent, el) { renderer.appendChild(parent, el); }

  createElement(renderer: Renderer2, elType: string, id = "", classes = "", innerhtml = "", style = ""): HTMLElement {
    const el = renderer.createElement(elType);
    renderer.setAttribute(el, "id", id);
    renderer.setAttribute(el, "class", classes);
    renderer.setProperty(el, 'innerHTML', innerhtml);
    renderer.setProperty(el, 'style', style);
    return el;
  }

}
