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
import { HostListener } from '@angular/core';
import { find } from 'rxjs/operators';
import { Inject } from '@angular/core';

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
  elementRef: ElementRef;

  constructor(private authenticationService: AuthenticationService, private activityService: ActivityService,
    private renderer: Renderer2, @Inject(ElementRef) elementRef: ElementRef) {
    this.elementRef = elementRef
  }

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
    this.buildActivityTableHead();
    this.LoadMonthDaysAndActivities();
    this.checkNavMonthButtons();
  }


  /* month linked functions (init & nav) */
  yearMonthToString(year: number, month: number): string {
    return String(this.targetYear) + "-" + String(this.targetMonth + 1).padStart(2, '0');
  }
  findDaysInMonth(year: number, month: number): number {
    return new Date(year, month, 0).getDate();
  }
  LoadMonthDaysAndActivities(): void {
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


  /*Activity table linked functions (dynamic table by current user, current teams and target month)*/
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
    const tB = this.tableBody;
    const crEl = this.createElement;
    const apd = this.append;
    let newCol, newRow, newHeader;

    /* Define thead columms witdth via html5 <col> */
    newCol = crEl(render, "col", "thead-colNom", "colNom", "", "width:15%")
    apd(render, tH.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.85 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "", "", "", "width:" + width + "%");
      apd(render, tH.nativeElement, newCol);
    }
    /* Add space blank row */
    newRow = crEl(render, "tr", "firstBlankRow");
    apd(render, tH.nativeElement, newRow);
    /* Add colums'headers row */
    newRow = crEl(render, "tr", this.yearMonth, "");
    newHeader = crEl(render, "th", "th-nom", "thead-col0-width", "Nom");
    apd(render, newRow, newHeader)
    for (let i = 0; i < this.daysInMonth; i++) {
      const day = String(i + 1).padStart(2, '0');
      newHeader = crEl(render, "th", this.yearMonth + "-" + day, "", day);
      apd(render, newRow, newHeader);
    }
    newHeader = crEl(render, "th", "th-checkActivity", "", "&#10003");
    apd(render, newRow, newHeader);
    apd(render, tH.nativeElement, newRow);
    /* Define tbody columms witdth via html5 <col> */
    newCol = crEl(render, "col", "tbody-colNom", "colNom", "", "width:15%")
    apd(render, tB.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.85 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "col" + (i + 1), "", "", "width:" + width + "%");
      apd(render, tB.nativeElement, newCol);
    }
  }
  loadMonthlyActivity(userId: number, username: string, isManager: boolean) {
    if (isManager) {
      this.activityService.getManagerTeamsMembers(userId).subscribe(
        (managerTeamsMembers: Employee[][]) => {
          managerTeamsMembers.forEach(tm => {
            this.teamsInit(tm);
          });
          this.loadActivities(this.yearMonth, username);
        },
        (error) => { alert(error); }
      );
    } else {
      this.activityService.getUserTeamMembers(username).subscribe(
        (userTeamMembers: Employee[]) => {
          console.log(userTeamMembers)
          this.teamsInit(userTeamMembers);
          this.loadActivities(this.yearMonth, username);
        },
        (error) => { alert(error); }
      );
    }
  }
  teamsInit(gottenTeam: Employee[]) {
    if (this.monthChange == 0) {
      (isNullOrUndefined(gottenTeam[0].team)) ?
        this.teams.push("") :
        this.teams.push(gottenTeam[0].team.name);
      console.log(this.teams);
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
    if (!isNullOrUndefined(teamActivities) && teamActivities.length > 0) {
      const membersWithActivities = teamActivities.filter(emp => emp != null)
      this.teamsMembers
        .filter(m => m.team.name == teamName)
        .forEach(m => {
          const memberWithActivities = membersWithActivities.find(e => e.username.match(m.username));
          if (memberWithActivities) {
            const tmp = memberWithActivities.listedActivities
              .filter(la => la.activity.date.startsWith(this.yearMonth))
            memberWithActivities.listedActivities = tmp;
            completed.push(memberWithActivities);
          } else {
            completed.push(new Employee(m.username, m.firstName, m.lastName, m.team, new Array(0)));
          }
        });
    } else {
      this.teamsMembers
        .filter(m => m.team.name == teamName)
        .map(m => {
          completed.push(new Employee(m.username, m.firstName, m.lastName, m.team, new Array(0)))
        });
    }
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
    let newRow, newCell, newDiv;

    /* insert Team name title-row */
    newRow = crEl(render, "tr", teamName + "-titleRow", "");
    newCell = crEl(render, "td", teamName + "-titleCell", "nom-equipe tbody-col0-width", teamName,)
    apd(render, newRow, newCell)
    apd(render, tB.nativeElement, newRow);
    /* insert each team member monthly activity row */
    teamActivities.forEach(member => {
      /* defines local boolean variables and associated list used to qualify each halDay */
      const isCurrentUser: boolean = (member.username == this.user.username);
      const closeDays = this.closeDaysInMonth;
      const openDays = this.openDaysInMonth;
      const listedActivities: ListedActivity[] = member.listedActivities
      const listedDays: number[] = listedActivities
        .filter(la => la.activity.halfDay == HalfDay.AM)
        .map(la => parseInt(la.activity.date.split('-')[2])).sort();
      const validatedDays: number[] = listedActivities
        .filter(la => la.isValidated && la.activity.halfDay == HalfDay.AM)
        .map(la => parseInt(la.activity.date.split('-')[2])).sort();
      const isMonthValidated: boolean =
        (listedDays.length == openDays.length) && (validatedDays.length == openDays.length);
      const userHighlight: string = (isCurrentUser && isMonthValidated) ? "background-color: lightslategray" :
        (isCurrentUser) ? "background-color: blanchedalmond" : "";

      console.log(listedActivities);

      /* insert employee row */
      newRow = crEl(render, "tr", member.username, "nom-prenom", "", userHighlight);
      newCell = crEl(render, "td", member.username + "nomPrenom", "", member.lastName + "," + member.firstName)
      apd(render, newRow, newCell)
      /* loop for each day in month */
      for (let i = 0; i < this.daysInMonth; i++) {
        /* set for each day local boolean variables and associated list used to qualify each halDay properties/display*/
        const day = String(i + 1).padStart(2, '0');
        const dayActivities = listedActivities.map(la => la.activity).filter(a => a.date.endsWith(day));
        const taskColorAM = dayActivities.filter(a => a.halfDay == HalfDay.AM).map(a => a.task.color).shift();
        const taskColorPM = dayActivities.filter(a => a.halfDay == HalfDay.PM).map(a => a.task.color).shift();
        const isListed: boolean = listedDays.includes(i + 1)
        const listedAMBgC: string = (isListed) ? "background-color: " + taskColorAM : "";
        const listedPMBgC: string = (isListed) ? "background-color: " + taskColorPM : "";
        const isClose: boolean = closeDays.includes(i + 1)
        const closeBgC: string = (isClose) ? "background-color: dimgrey" : "";
        const isOpen: boolean = openDays.includes(i + 1)
        const openBgC: string = (isOpen && !isListed) ? "background-color: lightgrey" : "";

        /* insert employee day with 2 halfdays in, setted/displayed according to booleans states */
        newCell = crEl(render, "td", member.username + "-" + day, "td-days");
        newDiv = crEl(render, "div", "div-" + member.username + "-" + day + "-" + "AM", taskColorAM, "", listedAMBgC + openBgC + closeBgC);
        (isCurrentUser && !isClose && !isMonthValidated) ?
          crSelTBC(render, "sel-" + member.username + "-" + day + "-" + "AM", newDiv, this.tasksList, crEl.bind(this)) : 1 > 1;
        apd(render, newCell, newDiv);
        newDiv = crEl(render, "div", "div-" + member.username + "-" + day + "-" + "PM", taskColorPM, "", listedPMBgC + openBgC + closeBgC);
        (isCurrentUser && !isClose && !isMonthValidated) ?
          crSelTBC(render, "sel-" + member.username + "-" + day + "-" + "PM", newDiv, this.tasksList, crEl.bind(this)) : 1 > 1;
        apd(render, newCell, newDiv);
        apd(render, newRow, newCell);
      }
      /* insert last cell of row "check for this employee month activities", setted/displayed according user role:
         adding a checkbox for manager, and a ckeck mark for user */
      if (this.isManager) {
        newCell = crEl(render, "td", member.username + "-" + this.yearMonth, "", "");
        const newIn = crEl(render, "input", member.username + "-" + this.yearMonth + "-check", "td-check");
        render.setAttribute(newIn, 'type', "checkbox");
        //render.setAttribute(newIn, 'checked', "false");
        newIn['checked'] = isMonthValidated;
        apd(render, newCell, newIn);
      } else {
        const checkStyle = (isMonthValidated) ? "color: black" : "color: lightgrey";
        newCell = crEl(render, "td", member.username + "-" + this.yearMonth, "td-check", "&#10003", checkStyle);
      }
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
  createSelectTaskByColor(renderer: Renderer2, id: string, parent: string, tasksList: Task[], createElement: Function) {
    createElement(renderer, "select", id = id)
    const newSel: HTMLElement = renderer.createElement("select");
    //renderer.listen(newSel, 'change', event => { onChange(event) });
    //renderer.setProperty(newSel, 'style', "{'background-color':selectedTaskColor}");
    tasksList.forEach(t => {
      const newOpt: HTMLElement = renderer.createElement("option");
      renderer.setProperty(newOpt, 'value', t.color);
      renderer.setProperty(newOpt, 'style', "background-color:" + t.color);
      renderer.appendChild(newSel, newOpt);
      //console.log(newOpt)
    })
    renderer.appendChild(parent, newSel);

  }

  @HostListener('change', ['$event'])
  onClick(event: any): void {
    /*get parent Id*/
    const parentId: String = event.path[1].id;
    const parent = this.elementRef.nativeElement.querySelector('#' + parentId);
    this.renderer.setProperty(parent, 'style', "background-color:" + event.target.value);

    const parentClass: String = event.path[1].classList.value;

    //.shift().substring(search)
    //@ViewChild(parentId, { static: false }) halfDayDiv: ElementRef;

    console.log(parent)
    console.log(parentId + " " + parentClass)
    console.log(event.path)
    console.log(event.target.value)
    console.log(event)

    /*     let parent = event.nativeElement.parentNode;
        this.elm.nativeElement.classList.add('red'); */
  }
}
