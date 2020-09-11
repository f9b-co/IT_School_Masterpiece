import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Component({
  selector: "app-login-account",
  templateUrl: "./login-account.component.html",
  styleUrls: ["./login-account.component.css"],
})
export class LoginAccountComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit() {}

  loginForm = new FormGroup({
    username: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(7),
        Validators.maxLength(7),
      ])
    ),
    password: new FormControl("", Validators.required),
    client_id: new FormControl("my-client-app"),
    grant_type: new FormControl("password"),
  });
  lF = this.loginForm;
  fc = this.loginForm.controls;

  onSubmit() {
    const url = "http://localhost:8081/oauth/token";
    const headers = new HttpHeaders().set(
      "Content-Type",
      "application/x-www-form-urlencoded"
    );
    const dataToSend = Object.keys(this.lF.value)
      .map((key) => key + "=" + this.lF.value[key])
      .join("&");
    console.log(dataToSend);

    // Send http request with form values to back api
    this.http.post(url, dataToSend, { headers }).subscribe(
      (data) => {
        this.lF.reset();
        window.sessionStorage.setItem("accessToken", data["access_token"]);
        console.log(data);
        console.log("Connexion réussie");
      },
      (error) => {
        console.log(error);
        console.log("Connexion impossible");
      }
    );
  }
}
