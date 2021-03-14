import { Injectable } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {
  private apiUrl = `${environment.baseUrl}/api`;
  private headers = new HttpHeaders().set("Content-Type", "application/json");

  constructor(private http: HttpClient) { }

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

  getMonthListedActivities(monthOffset, username): Observable<any> {
    const httpOptions = {
      headers: this.headers/* ,
      params: new HttpParams({fromString: `mo=${monthOffset}&ui=${userId}`}) */ };
    console.log(`${this.apiUrl}/employees/${username}/activities`);
    return this.http.get(`${this.apiUrl}/employees/${username}/activities`, httpOptions);
  }

  updateMonthListedActivities(): void {

  }

  unsubscribe(subscription: Subscription): void {
    if (subscription) {
      subscription.unsubscribe();
    }

  }

}
