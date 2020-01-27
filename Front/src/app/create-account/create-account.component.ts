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

  createForm = new FormGroup({
    identifiant: new FormControl("Axxxxxx", Validators.required),
    motDePasse: new FormControl("A+xxxxxx", Validators.required),
    confirmeMotDePasse: new FormControl("", Validators.required)
  });

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn("Valeurs du formulaire = " + this.createForm.value);
  }
}
