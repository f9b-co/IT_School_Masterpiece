import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: "app-create-userAccount",
  templateUrl: "./create-userAccount.component.html",
  styleUrls: ["./create-userAccount.component.css"],
})
export class CreateUserAccountComponent implements OnInit {
  constructor(private router: Router, private http: HttpClient,
    private authenticationService: AuthenticationService) { }

  private apiUrl = `${environment.apiUrl}`;
  private headers = new HttpHeaders().set("Content-Type", "application/json");
  private departments = [];

  ngOnInit() {
    this.getDepartments().subscribe(
      (data) => { this.departments = data; },
      (error) => { alert(error); }
    );
  }

  getDepartments(): Observable<any> {
    const httpOptions = { headers: this.headers };
    return this.http.get(`${this.apiUrl}/departments`, httpOptions);
  }

  setBlack() {
    document.querySelector("select.form-control").setAttribute('style', 'color: black')
  }

  createUserAccountForm = new FormGroup(
    {
      username: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(7),
          Validators.maxLength(7),
          Validators.pattern("^[axAX]{1}[0-9]{6}$")
        ])
      ),
      password: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20),
          Validators.pattern(
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|" +
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\W).*$|" +
            "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?\\W).*$|" +
            "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$"
          ),
        ])
      ),
      passwordCheck: new FormControl("", Validators.required),
      firstName: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(64),
          Validators.pattern("^[^<>=+*~&|\\\\/()\\[\\]\"'`,.;:!?@#$%]*$")
        ])
      ),
      lastName: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(64),
          Validators.pattern("^[^<>=+*~&|\\\\/()\\[\\]\"'`,.;:!?@#$%]*$")
        ])
      ),
      email: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.email,
          Validators.maxLength(128),
        ])
      ),
      department: new FormControl("", Validators.required),
    }
  );
  createForm = this.createUserAccountForm;
  controls = this.createUserAccountForm.controls;

  onSubmit() {
    const employeesUrl = `${environment.baseUrl}/api/employees/`;
    const headers = new HttpHeaders().set("Content-Type", "application/json");
    const dataToSend = JSON.stringify(this.createForm.value);
    Object.keys(dataToSend).forEach(function (key) {
      if (dataToSend[key] == "passwordCheck") delete dataToSend[key];
    });
    console.log(dataToSend);

    // Send http request with form values to back api
    this.http.post(employeesUrl, dataToSend, { headers }).subscribe(
      (data) => {
        this.createForm.reset();
        alert("Enregistrement réussi!");
        // Send http request to api for auto-login
        const sent = JSON.parse(dataToSend)
        const requiredAuthData =
          `username=${sent.username}&password=${sent.password}&client_id=masterpiece-spa&grant_type=password`;
        this.authenticationService.login(requiredAuthData).subscribe(
          (data) => {
            console.log(data);
            this.router.navigate(['/monthlyActivity']);
          },
          (error) => {
            alert("Connexion impossible\n" + error);
          }
        );
      },
      (error) => {
        alert("Enregistrement impossible \n" + error);
      }
    );
  }
}
