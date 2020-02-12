import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";

@Component({
  selector: "app-create-user",
  templateUrl: "./create-user.component.html",
  styleUrls: ["./create-user.component.css"]
})
export class CreateUserComponent implements OnInit {
  constructor() {}

  ngOnInit() {}

  createUserForm = new FormGroup({
    id: new FormControl("", Validators.required),
    firstname: new FormControl("", Validators.required),
    lastname: new FormControl("", Validators.required),
    email: new FormControl("", Validators.required),
    department: new FormControl("", Validators.required)
  });

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn("Valeurs du formulaire = ");
    const formKeys = Object.keys(this.createUserForm.value);
    formKeys.forEach(k => {
      console.log(k + " : " + this.createUserForm.get(k).value);
    });
  }
}
