import { Injectable } from '@angular/core';
import { forkJoin, Observable, Subscription } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })

/* Http service dedicated to monthlyActivity.component
 * to handle http requests to BackEnd, and return corresponding responses through Observables
 */
export class ActivityService {
  private apiUrl = `${environment.apiUrl}`;
  private headers = new HttpHeaders().set("Content-Type", "application/json");

  constructor(private http: HttpClient) { }

  listCloseAndOpenDays(): Observable<any> {
    return this.http.get<any>('../../assets/jsons/api-gouv-fr_jours-feries_2020-2022.json');
  }

  getAllTasks(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/tasks`, { headers: this.headers })
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

  saveListedActivities(creDto, updDto): Observable<any> {
    return forkJoin([
      this.http.post<any>(`${this.apiUrl}/listed-activities`, creDto, { headers: this.headers }),
      this.http.patch<any>(`${this.apiUrl}/listed-activities`, updDto, { headers: this.headers })
    ]).pipe(
      map((responses: any[]) => {
        let created: boolean = (JSON.parse(creDto).length > 0) ? responses[0] : null;
        let updated: boolean = (JSON.parse(updDto).length > 0) ? responses[1] : null;
        return new Array(created, updated);
      })
    );
  }

}
