import { Injectable } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })

export class ActivityService {
  private apiUrl = `${environment.apiUrl}`;
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
        const closedDays: number[] = [];
        for (let i = 1; i <= daysInMonth; i++) {
          const weekDay = new Date(year, month, i).getDay();
          if (weekDay == 0 || weekDay == 6 || publicHoliday.includes(i)) {
            closedDays.push(i);
          }
        }
        console.log(closedDays);
        return closedDays;
      });
  }

  getManagerTeamsMembers(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/employees/${userId}/teams-members`, { headers: this.headers })
  }
  getUserTeamMembers(username: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/employees/${username}/team-members`, { headers: this.headers })
  }


  getMonthListedActivities(yearMonth: string, team: string, username: string): Observable<any> {
    const httpOptions = {
      headers: this.headers,
      params: new HttpParams({ fromString: `team=${team}&period=${yearMonth}&username=${username}` })
    };
    return this.http.get(`${this.apiUrl}/employees/listed-activities`, httpOptions);
  }

  updateMonthListedActivities(): void {

  }

  unsubscribe(subscription: Subscription): void {
    if (subscription) {
      subscription.unsubscribe();
    }

  }

}
