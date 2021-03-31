import {
  Component, OnInit, AfterViewInit, Inject,
  HostListener, ElementRef, ViewChild, Renderer2
} from '@angular/core';
import { Router } from '@angular/router';
import { isNullOrUndefined } from 'util';

import { AuthenticationService } from '../_services/authentication.service';
import { ActivityService } from '../_services/activity.service';
import { User } from '../_models/user';
import { Role } from '../_models/role';
import { Employee } from '../_models/employee';
import { HalfDay } from '../_models/halfDay';
import { Task } from '../_models/task';
import { Activity } from '../_models/activity';
import { ListedActivity } from '../_models/listedActivity';

@Component({
  selector: 'app-monthlyActivity',
  templateUrl: './monthlyActivity.component.html',
  styleUrls: ['./monthlyActivity.component.css']
})
export class MonthlyActivityComponent implements AfterViewInit, OnInit {
  user: User = this.authenticationService.currentUserValue;
  welcomeMsg: String = "Bienvenue " + this.user.username;
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
  isModified: boolean = false;
  activitiesToCreate: ListedActivity[] = [];
  activitiesToUpdate: ListedActivity[] = [];
  modifiedElements: [String, String, number][] = [];
  iCreate: number = 0;
  iUpdate: number = 0;
  createsDone: any;
  updatesDone: any;

  constructor(private router: Router, private authenticationService: AuthenticationService, private activityService: ActivityService,
    private renderer: Renderer2, @Inject(ElementRef) elementRef: ElementRef) {
    this.elementRef = elementRef
  }

  @ViewChild('activitiesTableHead', { static: false }) tableHead: ElementRef;
  @ViewChild('activitiesTableBody', { static: false }) tableBody: ElementRef;
  @ViewChild('tasksTable', { static: false }) tasksTable: ElementRef;

  ngOnInit() {
    setTimeout(() => this.welcomeMsg = "\xa0", 10000);
    this.loadTasks();
  }

  ngAfterViewInit() {
    this.monthDisplay();
  }

  ngAfterViewChecked() {
    this.tasksTable.nativeElement.innerHTML = "";
    this.diplayTasksRefList(this.tasksList);
  }


  /*month linked functions (init & nav)*/
  monthDisplay(): void {
    this.tableHead.nativeElement.innerHTML = "";
    this.tableBody.nativeElement.innerHTML = "";

    this.daysInMonth = this.findDaysInMonth(this.targetMonth + 1, this.targetYear);
    this.yearMonth = this.yearMonthToString(this.targetYear, this.targetMonth)
    this.buildActivityTableHead();
    this.LoadMonthDaysAndActivities();
    this.checkNavMonthButtons();
  }
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
    this.monthDisplay();
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
    this.monthDisplay();
  }
  checkNavMonthButtons(): void {
    const prev = document.getElementById("previous");
    const next = document.getElementById("next");
    (this.isTooLow) ? prev.style.visibility = "hidden" : prev.style.visibility = "visible";
    (this.isTooHigh) ? next.style.visibility = "hidden" : next.style.visibility = "visible";
  }


  /*Activity table linked functions (dynamic table by current user, current teams and target month)*/
  loadTasks(): void {
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
    let newCol, newRow, newHeader, newDiv;

    /* Define thead columms witdth via html5 <col> */
    newCol = crEl(render, "col", "thead-colNom", "colNom", "", "width:13%")
    apd(render, tH.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.85 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "", "", "", "width:" + width + "%");
      apd(render, tH.nativeElement, newCol);
    }
    newCol = crEl(render, "col", "thead-scrollBarSpace", "", "", "width: 1.5%");
    apd(render, tH.nativeElement, newCol);
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
    render.setAttribute(newHeader, 'title', "validation\noui/non");
    apd(render, newRow, newHeader);
    apd(render, tH.nativeElement, newRow);
    newHeader = crEl(render, "td", "td-scrollBarSpace");
    newDiv = crEl(render, "div", "div-scrollBarSpace", "hidden", "&#8863;");
    apd(render, newHeader, newDiv);
    apd(render, newRow, newHeader);
    apd(render, tH.nativeElement, newRow);
    /* Define tbody columms witdth via html5 <col> */
    newCol = crEl(render, "col", "tbody-colNom", "colNom", "", "width:13.5%")
    apd(render, tB.nativeElement, newCol);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      const width = 0.855 / (this.daysInMonth + 1) * 100;
      newCol = crEl(render, "col", "col" + (i + 1), "", "", "width:" + width + "%");
      apd(render, tB.nativeElement, newCol);
    }
    newCol = crEl(render, "col", "tbody-scrollBarSpace", "", "", "width: 0%");
    apd(render, tB.nativeElement, newCol);
  }
  loadMonthlyActivity(userId: number, username: string, isManager: boolean): void {
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
  teamsInit(gottenTeam: Employee[]): void {
    if (this.monthChange == 0) {
      (isNullOrUndefined(gottenTeam[0].team)) ?
        this.teams.push("") :
        this.teams.push(gottenTeam[0].team.name);
      console.log(this.teams);
      gottenTeam.forEach(member => this.teamsMembers.push(member));
    }
  }
  loadActivities(yearMonth: string, username: string): void {
    if (this.teams.length > 0) {
      this.teams.forEach(t => {
        this.getTeamActivities(yearMonth, t, username);
      });
    } else {
      alert("pas d'employés à afficher!");
    }
  }
  getTeamActivities(yearMonth: string, teamName: string, username: string): void {
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
  completeExtractedActivities(teamName: string, teamActivities: Employee[]): Employee[] {
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
            completed.push(new Employee(m.username, m.firstName, m.lastName, m.team, m.id, new Array(0)));
          }
        });
    } else {
      this.teamsMembers
        .filter(m => m.team.name == teamName)
        .map(m => {
          completed.push(new Employee(m.username, m.firstName, m.lastName, m.team, m.id, new Array(0)))
        });
    }
    return completed;
  }
  tableize(teamName: string, teamActivities: Employee[]): void {
    console.log(teamName);
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
      /* defines local variables and associated list used to qualify each halfday */
      const isCurrentUser: boolean = (member.username == this.user.username);
      const closeDays = this.closeDaysInMonth;
      const openDays = this.openDaysInMonth;
      const listedActivities: ListedActivity[] = member.listedActivities
      const listeHalfdDays: number = listedActivities.length;
      const validatedHalfDays: number = listedActivities.filter(la => la.isValidated == true).length;
      const isMonthValidated: boolean =
        (listeHalfdDays == openDays.length * 2) && (validatedHalfDays == openDays.length * 2);
      const userHighlight: string = (isCurrentUser && isMonthValidated) ? "background-color: lightslategray" :
        (isCurrentUser) ? "background-color: blanchedalmond" : "";

      console.log(listedActivities);

      /* insert employee row */
      newRow = crEl(render, "tr", member.username, "nom-prenom", "", userHighlight);
      newCell = crEl(render, "td", member.username + "nomPrenom", "tbody-col0-width", member.lastName + "," + member.firstName)
      apd(render, newRow, newCell)
      /* loop for each day in month */
      for (let i = 0; i < this.daysInMonth; i++) {
        /* set for each day local variables and associated list used to qualify each halfday properties/display*/
        const day = String(i + 1).padStart(2, '0');
        const isOpen: boolean = openDays.includes(i + 1);
        const isClose: boolean = closeDays.includes(i + 1);
        const closeBgC: string = (isClose) ? "background-color: dimgrey" : "";
        const halfdays: HalfDay[] = [HalfDay.AM, HalfDay.PM];
        const dayActivities = listedActivities.map(la => la.activity).filter(a => a.date.endsWith(day));
        /* insert employee day with 2 halfdays in, setted/displayed according to booleans states */
        newCell = crEl(render, "td", member.username + "-" + day, "td-days");
        halfdays.forEach(hd => {
          const taskColor = dayActivities.filter(a => a.halfDay == hd).map(a => a.task.color).shift();
          const isListed: boolean = dayActivities.filter(a => a.halfDay == hd).length == 1;
          const listedBgC: string = (isListed) ? "background-color: " + taskColor : "";
          const openBgC: string = (isOpen && !isListed) ? "background-color: lightgrey" : "";
          newDiv = crEl(render, "div", `div-${member.id}-${member.username}-${day}-${hd}-${isListed}`, hd, "", listedBgC + openBgC + closeBgC);
          (isCurrentUser && !isClose && !isMonthValidated) ?
            crSelTBC(render, `sel-${member.id}-${member.username}-${day}-${hd}-${isListed}`, newDiv,
              this.tasksList, crEl.bind(this)) : 1 > 1;
          apd(render, newCell, newDiv);

        })
        apd(render, newRow, newCell);
      }
      /* insert last cell of row "check for this employee month activities", setted/displayed according user role:
         adding a checkbox for manager, and a ckeck mark for user */
      if (this.isManager) {
        newCell = crEl(render, "td", `${member.username}-${this.yearMonth}`, "", "");
        const newIn = crEl(render, "input", `che-${member.username}-${this.yearMonth}-check`, "td-check");
        render.setAttribute(newIn, 'type', "checkbox");
        render.setAttribute(newIn, 'title', "figer\ndéfiger");
        newIn['checked'] = isMonthValidated;
        apd(render, newCell, newIn);
      } else {
        const checkStyle = (isMonthValidated) ? "color: black" : "color: lightgrey";
        newCell = crEl(render, "td", `${member.username}-${this.yearMonth}`, "td-check", "&#10003", checkStyle);
        render.setAttribute(newCell, 'title', "noir = oui\ngris = non");
      }
      apd(render, newRow, newCell);
      apd(render, tB.nativeElement, newRow);
    });
    newRow = crEl(render, "tr", teamName + "spaceRow", "");
    newCell = crEl(render, "td", teamName + "td-spaceRow-col0");
    newDiv = crEl(render, "div", "div-spaceRow-col0", "div-spaceRow-col0");
    apd(render, newCell, newDiv);
    apd(render, newRow, newCell);
    for (let i = 0; i < this.daysInMonth + 1; i++) {
      newCell = crEl(render, "td", teamName + "td-spaceRow-day" + i + 1)
      newDiv = crEl(render, "div", "div-spaceRow-day" + i + 1, "div-spaceRow-days");
      apd(render, newCell, newDiv);
      apd(render, newRow, newCell);
    }
    apd(render, tB.nativeElement, newRow);
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
  saveChanges(): void {
    if (this.activitiesToCreate.length > 0) {
      this.sendToCreate()
    }
    if (this.activitiesToUpdate.length > 0) {
      this.sendToUpdate()
    }
  }
  sendToCreate(): void {
    this.activityService.postListedActivities(JSON.stringify(this.activitiesToCreate)).subscribe(
      (ok) => {
        this.activitiesToCreate.length = 0;
        this.iCreate = 0;
        if (this.activitiesToUpdate.length = 0) {
          this.modifiedElements.length = 0;
          this.isModified = false;
        }
        alert("Nouvelles activitées enregistrées")
      },
      (error) => { alert(error); }
    );
  }
  sendToUpdate(): void {
    this.activityService.patchListedActivities(JSON.stringify(this.activitiesToUpdate)).subscribe(
      (ok) => {
        this.activitiesToUpdate.length = 0;
        this.iUpdate = 0;
        if (this.activitiesToCreate.length = 0) {
          this.modifiedElements.length = 0;
          this.isModified = false;
        }
        alert("Modifications enregistrées")
      },
      (error) => { alert(error); }
    );
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

    const newSel: HTMLElement = createElement(renderer, "select", id); //renderer.createElement("select");
    for (let i = 1; i < tasksList.length; i++) {
      const newOpt: HTMLElement = renderer.createElement("option");
      renderer.setProperty(newOpt, 'value', tasksList[i].color);
      renderer.setProperty(newOpt, 'style', "background-color:" + tasksList[i].color);
      renderer.appendChild(newSel, newOpt);
    }
    renderer.appendChild(parent, newSel);
  }

  @HostListener('change', ['$event'])
  onClick(event: any): void {
    console.log(event);
    console.log(event.path);
    console.log(event.target.value + " / " + event.target.checked);
    /* get el & parent Id &  HTMLElement*/
    const parentId: String = event.path[1].id;
    const targetElId: String = event.target.id;
    console.log(parentId);
    console.log(targetElId);
    const parent: HTMLElement = this.elementRef.nativeElement.querySelector('#' + parentId);
    const targetEl: HTMLElement = this.elementRef.nativeElement.querySelector('#' + targetElId);
    const idStartWith: String = event.target.type.substr(0, 3) + "-";
    let isValidated: boolean = false
    let taskColor: String = event.target.value
    let listedActivity: ListedActivity;
    /* update Html according to changes type and stock changes for backEnd in array */
    switch (idStartWith) {
      case ("sel-"):
        isValidated = false;
        taskColor = event.target.value;
        /* update div BGcolor */
        this.renderer.setProperty(parent, 'style', "background-color:" + taskColor);
        listedActivity = this.changeToObject(targetElId, taskColor, isValidated);
        this.checkIfAllreadyAndPushToUpdate(targetElId, listedActivity);
        break;
      case ("che-"):
        isValidated = event.target.checked;
        console.log(event.target.checked);
        /* update row BGcolor */
        const rowsHighlight: string =
          (event.target.checked) ? "background-color: lightsteelblue" : "background-color: none";
        const row: HTMLElement = event.target.parentElement.parentElement;
        this.renderer.setProperty(row, 'style', rowsHighlight)
        /* stock listedActivity change for backEnd */
        const childrenList = Array.from(row.children);
        for (let el of childrenList) {
          if (el.classList.contains("td-days")) {
            let divList = Array.from(el.children);
            for (const subEl of divList) {
              taskColor = subEl.getAttribute("style").replace("background-color: ", "").replace(";", "");
              if (!taskColor.match("dimgrey")) {
                console.log(event.target.checked);
                listedActivity = this.changeToObject(subEl.id, taskColor, isValidated);
                this.checkIfAllreadyAndPushToUpdate(subEl.id, listedActivity);
              }
            }
          }
        }
        break;
      default:
        break;
    }
    console.log(this.modifiedElements);
    console.log(this.activitiesToCreate);
    console.log(this.activitiesToUpdate);
    console.log("isModified = " + this.isModified)
    console.log(event);
  }
  checkIsModified() {
    this.isModified = (this.modifiedElements.length == 0) ? false : true;
  }
  changeToObject(targetElId, taskColor, isValidated): ListedActivity {
    /* stock change in new ListedActivity Object */
    const date: String = this.yearMonth + "-" + targetElId.split("-")[3];
    const halfday: HalfDay = <HalfDay>targetElId.split("-")[4];
    const task: Task = this.tasksList.find(t => t.color == taskColor);
    const activity: Activity = new Activity(date, halfday, task)
    const idToUse = (this.isManager) ? targetElId.split("-")[1] : this.user.userId;
    return new ListedActivity(activity, isValidated, idToUse);
  }
  checkIfAllreadyAndPushToUpdate(touchedId: String, listedActivity: ListedActivity): any {
    /* check if a change for ab halfDay is allready in create or update array waiting to be sent to backEnd,
       if yes, replace it by the new one */
    const iAllready = this.modifiedElements.indexOf(this.modifiedElements.find(el => el[0] == touchedId));
    if (iAllready >= 0) {
      const old: [String, String, number] = this.modifiedElements.find(el => el[0] == touchedId);
      let indexToDelete: number;
      if (old[1].match("upd")) {
        indexToDelete = this.activitiesToUpdate.indexOf(this.activitiesToUpdate.find(el => el[1] == old[2]));
        this.activitiesToUpdate.splice(indexToDelete, 1);
      } else {
        indexToDelete = this.activitiesToCreate.indexOf(this.activitiesToCreate.find(el => el[1] == old[2]));
        this.activitiesToCreate.splice(indexToDelete, 1);
      }
      this.modifiedElements.splice(iAllready, 1);
    }
    /* add new change in create or update array to be sent to backEnd */
    if (touchedId.split("-")[5] == "false") {
      this.activitiesToCreate.push(listedActivity)
      this.modifiedElements.push([touchedId, "cre", this.iCreate]);
      this.iCreate++;
      this.checkIsModified();

    } else {
      this.activitiesToUpdate.push(listedActivity)
      this.modifiedElements.push([touchedId, "upd", this.iUpdate]);
      this.iUpdate++;
      this.checkIsModified();
    }
  }

  @HostListener('window:beforeunload', ['$event'])
  canDeactivate(event: BeforeUnloadEvent): void {
    // if unsaved changes, show confirm dialog box to the user to decide to stay in the page or not
    if (this.activitiesToUpdate.length > 0) {
      event.returnValue = confirm("!!! Modification(s) non sauvegardée(s) !!!. Voulez-vous quitez la page quand même?");
    }
  }
}
