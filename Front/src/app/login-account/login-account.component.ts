import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";

@Component({
  selector: "app-login-account",
  templateUrl: "./login-account.component.html",
  styleUrls: ["./login-account.component.css"]
})
export class LoginAccountComponent implements OnInit {
  constructor() {}

  ngOnInit() {}

  loginForm = new FormGroup({
    id: new FormControl(
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
          "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?W).*$|^(?=.*?[a-z])(?=.*?[0-9])(?=.*?W).*$|^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?W).*$"
        )
      ])
    )
  });

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn("Valeurs du formulaire = ");
    const formKeys = Object.keys(this.loginForm.value);
    formKeys.forEach(k => {
      console.log(k + " : " + this.loginForm.get(k).value);
    });
  }
}