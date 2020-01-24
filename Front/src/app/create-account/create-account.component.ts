import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";

@Component({
  selector: "app-create-account",
  templateUrl: "./create-account.component.html",
  styleUrls: ["./create-account.component.css"]
})
export class CreateAccountComponent implements OnInit {
  constructor() {}

  ngOnInit() {}

  createForm = new FormGroup({
    identifiant: new FormControl(""),
    motDePasse: new FormControl(""),
    confirmeMotDePasse: new FormControl(""),
    valider: new FormControl("")
  });

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.createForm.value);
  }
}
