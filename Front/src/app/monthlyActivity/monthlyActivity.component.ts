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
import { HalfDay } from '../_models/halfDay';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements AfterViewInit, OnInit {
  user: User = this.authenticationService.currentUserValue;
  isManager: boolean = this.user.roles.includes(Role.Manager);
  teams: string[] = [];
  tasksList: Task[] = [];
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
  closeDaysInMonth: number[] = [];
  openDaysInMonth: number[] = [];
  selectedTaskColor: string = "";

  constructor(private authenticationService: AuthenticationService,
    private activityService: ActivityService, private renderer: Renderer2) { }

  @ViewChild('activitiesTableHead', { static: false }) tableHead: ElementRef;
  @ViewChild('activitiesTableBody', { static: false }) tableBody: ElementRef;
  @ViewChild('tasksTable', { static: false }) tasksTable: ElementRef;

  ngOnInit() {
    this.loadTasks();
  }

  ngAfterViewInit() {
    this.tableHead.nativeElement.innerHTML = "";
    this.tableBody.nativeElement.innerHTML = "";
    this.tasksTable.nativeElement.innerHTML = "";

    this.daysInMonth = this.findDaysInMonth(this.targetMonth + 1, this.targetYear);
    this.yearMonth = this.yearMonthToString(this.targetYear, this.targetMonth)
    this.LoadOpenAndCloseDays();
    this.buildActivityTableHead();
    this.checkNavMonthButtons();
  }


  /*month about functions (init & nav)*/
  yearMonthToString(year: number, month: number): string {
    return String(this.targetYear) + "-" + String(this.targetMonth + 1).padStart(2, '0');
  }
  findDaysInMonth(year: number, month: number): number {
    return new Date(year, month, 0).getDate();
  }
  LoadOpenAndCloseDays() {
    this.openDaysInMonth.length = 0;
    this.closeDaysInMonth.length = 0;
    this.activityService.listCloseAndOpenDays()
      .subscribe((res: any) => {
        const publicHoliday = [];
        const year = this.targetYear;
        const month = this.targetMonth;
        for (let date in res) {
          const monthDay = new Date(date)
          if (monthDay.getFullYear() == year && monthDay.getMonth() == month) {
            publicHoliday.push(monthDay.getDate());
          }
        }
        console.log(year + "-" + month + " publicHoliday = " + publicHoliday)
        for (let i = 1; i <= this.daysInMonth; i++) {
          const weekDay = new Date(year, month, i).getDay();
          if (weekDay == 0 || weekDay == 6 || publicHoliday.includes(i)) {
            this.closeDaysInMonth.push(i);
          } else {
            this.openDaysInMonth.push(i);
          }
        }
        this.loadMonthlyActivity(this.user.userId, this.user.username, this.isManager);
      },
        (error) => { alert(error); }
      );
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


  /*Activity table about functions (dynamic table by current user, current teams and target month)*/
  loadTasks() {
    this.activityService.getAllTasks().subscribe(
      (tasksList: Task[]) => {
        tasksList.forEach(task => this.tasksList.push(task));
        console.log(this.tasksList);
      },
      (error) => { alert(error); }
    );
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
    console.log(this.closeDaysInMonth);
    if (isManager) {
      this.activityService.getManagerTeamsMembers(userId).subscribe(
        (managerTeamsMembers: Employee[][]) => {
          managerTeamsMembers.forEach(tm => {
            this.teamsInit(userId, tm);
          });
          this.loadActivities(this.yearMonth, username);
        },
        (error) => { alert(error); }
      );
    } else {
      this.activityService.getUserTeamMembers(username).subscribe(
        (userTeamMembers: Employee[]) => {
          this.teamsInit(username, userTeamMembers);
          this.loadActivities(this.yearMonth, username);
        },
        (error) => { alert(error); }
      );
    }
  }
  teamsInit(userRef: any, gottenTeam: Employee[]) {
    if (this.monthChange == 0) {
      (isNullOrUndefined(gottenTeam[0].team)) ?
        this.teams.push("") :
        this.teams.push(gottenTeam[0].team.name);
      gottenTeam.forEach(member => this.teamsMembers.push(member));
    }
  }
  loadActivities(yearMonth: string, username: string) {
    this.diplayTasksRefList(this.tasksList);
    if (this.teams.length > 0) {
      this.teams.forEach(t => {
        this.getTeamsActivities(yearMonth, t, username);
      });
    } else {
      alert("pas d'employés à afficher!");
    }
    console.log(this.teams)
  }
  getTeamsActivities(yearMonth: string, teamName: string, username: string): any {
    this.activityService.getMonthListedActivities(yearMonth, teamName, username).subscribe(
      (teamActivities: Employee[]) => {
        console.log(teamActivities);
        const allMembersActivitiesLists: Employee[] = this.completeExtractedActivities(teamName, teamActivities);
        this.tableize(teamName, allMembersActivitiesLists);
      },
      (error) => {
        alert(error);
      }
    );
  }
  completeExtractedActivities(teamName: string, teamActivities: Employee[]) {
    /*if no activities are retrieved from DB for a month for a team member, 
    create substitute empty listedActivities[]*/
    const completed: Employee[] = [];
    /*at least one team member have listed activities, else no one have...*/
    if (!isNullOrUndefined(teamActivities[0])) {
      this.teamsMembers
        .filter(m => m.team.name == teamName)
        .map(m => {
          const tmpEmp: Employee = teamActivities.filter(employee => employee.username == m.username)[0];
          if (isNullOrUndefined(tmpEmp)) {
            completed.push(new Employee(m.username, m.firstName, m.lastName, m.team, new Array(0)));
          } else {
            completed.push(tmpEmp);
          }
        });
    } else {
      this.teamsMembers
        .filter(m => m.team.name == teamName)
        .map(m => {
          completed.push(new Employee(m.username, m.firstName, m.lastName, m.team, new Array(0)))
        });
    }
    completed.forEach(emp => emp.listedActivities
      .filter(la => la.activity.date.startsWith(this.yearMonth)));

    return completed;
  }
  tableize(teamName: string, teamActivities: Employee[]): void {
    console.log(teamName);
    //console.log(teamActivities);
    const render = this.renderer;
    const tB = this.tableBody;
    const crEl = this.createElement;
    const apd = this.append;
    const sElP = this.setElProperty;
    const mElC = this.makeElClickable;
    const crSelTBC = this.createSelectTaskByColor;
    let newCol, newRow, newCell, newDiv;

    /* Define columms witdth via html5 <col> */
    newCol = crEl(render, "col", "colNom", "", "", "width:15%")
    apd(render, tB.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.85 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "col" + (i + 1), "", "", "width:" + width + "%");
      apd(render, tB.nativeElement, newCol);
    }
    /* insert Team name title-row */
    newRow = crEl(render, "tr", teamName + "-titleRow", "");
    newCell = crEl(render, "td", teamName + "-titleCell", "nom-equipe", teamName)
    apd(render, newRow, newCell)
    apd(render, tB.nativeElement, newRow);
    /* insert each team member monthly activity row */
    teamActivities.forEach(member => {
      const isCurrentUser: boolean = (member.username == this.user.username);
      const userHighlight: string = (isCurrentUser) ? "background-color: blanchedalmond" : "";
      const closeDays = this.closeDaysInMonth;
      const openDays = this.openDaysInMonth;
      const ListedActivities: ListedActivity[] = member.listedActivities
        .filter(la => la.activity.date.startsWith(this.yearMonth));
      const listedDays: number[] = ListedActivities
        .filter(la => la.activity.halfDay == HalfDay.AM)
        .map(la => parseInt(la.activity.date.split('-')[2])).sort();
      const validatedDays: number[] = ListedActivities
        .filter(la => la.isValidated && la.activity.halfDay == HalfDay.AM)
        .map(la => parseInt(la.activity.date.split('-')[2])).sort();
      const validatedMonth: boolean =
        (listedDays.length == openDays.length) && (validatedDays.length == openDays.length);

      console.log(ListedActivities);

      newRow = crEl(render, "tr", member.username, "nom-prenom", "", userHighlight);
      newCell = crEl(render, "td", member.username + "nomPrenom", "", member.lastName + "," + member.firstName)
      apd(render, newRow, newCell)
      for (let i = 0; i < this.daysInMonth; i++) {
        const day = String(i + 1).padStart(2, '0');
        const dayActivities = ListedActivities.map(la => la.activity).filter(a => a.date.endsWith(day));
        const taskColorAM = dayActivities.filter(a => a.halfDay == HalfDay.AM).map(a => a.task.color);
        const taskColorPM = dayActivities.filter(a => a.halfDay == HalfDay.PM).map(a => a.task.color);
        const isListed: boolean = listedDays.includes(i + 1)
        const listedAMBgC: string = (isListed) ? "background-color: " + taskColorAM : "";
        const listedPMBgC: string = (isListed) ? "background-color: " + taskColorPM : "";
        const isClose: boolean = closeDays.includes(i + 1)
        const closeBgC: string = (isClose) ? "background-color: dimgrey" : "";
        const isOpen: boolean = openDays.includes(i + 1)
        const openBgC: string = (isOpen && !isListed) ? "background-color: lightgrey" : "";

        newCell = crEl(render, "td", member.username + "-" + day, "days-td");
        newDiv = crEl(render, "div", member.username + "-" + day + "-" + "AM", "", "", listedAMBgC + openBgC + closeBgC);
        (isCurrentUser && !isClose && !validatedMonth) ? mElC(render, newDiv, "OnClickScript") : 1 > 1;
        //(isCurrentUser && !isClose && !validatedMonth) ? crSelTBC(render, newDiv) : 1 > 1;
        apd(render, newCell, newDiv);
        newDiv = crEl(render, "div", member.username + "-" + day + "-" + "PM", "", "", listedPMBgC + openBgC + closeBgC);
        (isCurrentUser && !isClose && !validatedMonth) ? mElC(render, newDiv, "OnClickScript") : 1 > 1;
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
  diplayTasksRefList(tasksList: Task[]) {
    const render = this.renderer;
    const tT = this.tasksTable;
    const crEl = this.createElement;
    const apd = this.append;
    let newCol, newRow, newCell, newDiv;
    /* Define columms witdth via html5 <col> */
    for (let i = 0; i < 5; i++) {
      const width = 1 / 5 * 100;
      newCol = crEl(render, "col", "col" + (i + 1), "", "", "width:" + width + "%");
      apd(render, tT.nativeElement, newCol);
    }
    /* insert as many row as needed to display table caption for all tasks, 5 by row */
    for (let i = 1; i < tasksList.length + 1; i++) {
      newRow = crEl(render, "tr",);
      for (let j = 1; j < 6; j++) {
        const task = tasksList[i - 1];
        newCell = crEl(render, "td",);
        newDiv = crEl(render, "div", "", "taskColor", "", "background-color:" + task.color,);
        apd(render, newCell, newDiv);
        newDiv = crEl(render, "div", "", "taskName", task.name);
        apd(render, newCell, newDiv);
        apd(render, newRow, newCell);
        if (i < tasksList.length && j % 5 > 0) { i++; }
        else if (i < tasksList.length) { }
        else { break; }
      }
      apd(render, tT.nativeElement, newRow);
    }
  }


  /*Custom DOM interraction functions*/
  createElement(renderer: Renderer2, elType: string, id = "", classes = "", innerhtml = "", style = ""): HTMLElement {
    const el = renderer.createElement(elType);
    renderer.setAttribute(el, 'id', id);
    renderer.setAttribute(el, 'class', classes);
    renderer.setProperty(el, 'innerHTML', innerhtml);
    renderer.setProperty(el, 'style', style);
    return el;
  }
  setElProperty(renderer: Renderer2, el: any, name: string, value: any): void {
    renderer.setProperty(el, name, value);
  }
  makeElClickable(renderer: Renderer2, el: any, script: string) {
    renderer.setProperty(el, "(click)", script);
    renderer.addClass(el, "clickable")
  }
  append(renderer: Renderer2, parent, el): void {
    renderer.appendChild(parent, el);
  }
  createSelectTaskByColor(renderer: Renderer2, parent: string) {
    const newSel: HTMLElement = renderer.createElement("select");
    renderer.setProperty(newSel, '(change)', "onChange($event.target.color)");
    renderer.setProperty(newSel, '[ngStyle]=', "{'background-color':selectedTaskColor}");
    const newOpt: HTMLElement = renderer.createElement("option");
    renderer.setProperty(newOpt, '*ngFor', "let task of tasksList");
    renderer.setProperty(newOpt, '[value]', "task.color");
    renderer.setProperty(newOpt, '[ngStyle]', "{'background-color':task.color}");
    renderer.appendChild(newSel, newOpt);
    renderer.appendChild(parent, newSel);
  }
  onChange(color: string): void {
    this.selectedTaskColor = color;
  }
}
