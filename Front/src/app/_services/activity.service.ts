import { Injectable } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {
  private activitiesUrl = `${environment.baseUrl}/api/activities/`;  
  private headers = new HttpHeaders().set("Content-Type", "application/json");

  constructor(private http: HttpClient) { }

  getMonthListedActivities(monthOffset, userId): Observable<any> {
    const httpOptions = { 
      headers: this.headers,
      params: new HttpParams({fromString: `mo=${monthOffset}&ui=${userId}`}) };
    return this.http.get(this.activitiesUrl, httpOptions);
  }

  updateMonthListedActivities(): void {

  }

  unsubscribe(subscription: Subscription): void {
    if (subscription) {
        subscription.unsubscribe();
    }
    
}

}
