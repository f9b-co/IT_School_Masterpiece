import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormGroup, FormControl, Validators } from "@angular/forms";

import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: "app-login-account",
  templateUrl: "./login-account.component.html",
  styleUrls: ["./login-account.component.css"],
})
export class LoginAccountComponent implements OnInit {
  private requiredAuthData: string;
  returnUrl: string;

  constructor(private router: Router, private authenticationService: AuthenticationService) {
    // redirect to main page if already logged in
    if (this.authenticationService.isCurrentUserLoggedIn()) {
      this.router.navigate(['/monthlyActivity']);
    }
  }

  ngOnInit() { }

  userLoginForm = new FormGroup({
    username: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(7),
        Validators.maxLength(7),
      ])
    ),
    password: new FormControl("", Validators.required),
    client_id: new FormControl("masterpiece-spa"),
    grant_type: new FormControl("password"),
  });
  loginForm = this.userLoginForm;
  controls = this.userLoginForm.controls;

  onSubmit() {
    this.requiredAuthData = Object.keys(this.loginForm.value)
      .map((key) => key + "=" + this.loginForm.value[key])
      .join("&");

    // Send http request with form values to back api
    this.authenticationService.login(this.requiredAuthData).subscribe(
      (data) => {
        console.log(data);
        this.router.navigate(['/monthlyActivity']);
      },
      (error) => {
        alert("Connexion impossible\n"+error);
      }
    );
  }
}
