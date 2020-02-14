import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { passwordChecking } from "../validators/passwordChecking";

@Component({
  selector: "app-create-account",
  templateUrl: "./create-account.component.html",
  styleUrls: ["./create-account.component.css"]
})
export class CreateAccountComponent implements OnInit {
  constructor() {}

  ngOnInit() {}

  createAccountForm = new FormGroup(
    {
      login: new FormControl(
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
      ),
      passwordCheck: new FormControl("", Validators.required)
    },
    { validators: passwordChecking }
  );

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn("Valeurs du formulaire = ");
    const formKeys = Object.keys(this.createAccountForm.value);
    formKeys.forEach(k => {
      console.log(k + " : " + this.createAccountForm.get(k).value);
    });
  }
}
