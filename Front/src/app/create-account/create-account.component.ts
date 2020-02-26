import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from "@angular/common/http";
import { catchError } from 'rxjs/operators'
import { throwError } from 'rxjs';
import { passwordChecking } from "../validators/passwordChecking";

@Component({
  selector: "app-create-account",
  templateUrl: "./create-account.component.html",
  styleUrls: ["./create-account.component.css"]
})
export class CreateAccountComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit() {}

  createAccountForm = new FormGroup(
    {
      username: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(7),
          Validators.maxLength(7)
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
          )
        ])
      ),
      passwordCheck: new FormControl("", Validators.required)
    },
    { validators: passwordChecking }
  );

  cAF = this.createAccountForm;
  fc = this.createAccountForm.controls;

  onSubmit() {
    const apiUrl = "http://localhost:8081/users/";
    const patchUrl = apiUrl + this.cAF.get("username").value + "/changeP";
    const headers = new HttpHeaders()
      .set("access-control-allow-origin", "http://localhost:8081")
      .set("Content-Type", "application/json");
    // Create FormData from FormGroup without passworCheck Key/Value to send to the Back in JSON
    const formToSend =
      JSON.stringify(this.cAF.value)
        .split(",", 2)
        .join(",") + "}";
    // Send http request with needed form values to back api
    this.http.put(patchUrl, formToSend, { headers }).subscribe(
      data => {
        this.cAF.reset();
        console.warn("password changé");
      },
      error => {
        console.warn("erreur!");
      }
    );
  }
}
