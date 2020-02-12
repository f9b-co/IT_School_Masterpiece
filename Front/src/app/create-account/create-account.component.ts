import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";

@Component({
  selector: "app-create-account",
  templateUrl: "./create-account.component.html",
  styleUrls: ["./create-account.component.css"]
})
export class CreateAccountComponent implements OnInit {
  constructor() {}

  ngOnInit() {}

  createAccountForm = new FormGroup({
    id: new FormControl("", Validators.required),
    password: new FormControl("", Validators.required),
    passwordCheck: new FormControl("", Validators.required)
  });

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn("Valeurs du formulaire = ");
    const formKeys = Object.keys(this.createAccountForm.value);
    formKeys.forEach(k => {
      console.log(k + " : " + this.createAccountForm.get(k).value);
    });
  }
}
