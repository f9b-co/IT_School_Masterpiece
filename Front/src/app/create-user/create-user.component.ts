import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-create-user",
  templateUrl: "./create-user.component.html",
  styleUrls: ["./create-user.component.css"]
})
export class CreateUserComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit() {}

  createUserForm = new FormGroup({
    login: new FormControl("", Validators.required),
    firstname: new FormControl("", Validators.required),
    lastname: new FormControl("", Validators.required),
    email: new FormControl("", Validators.required),
    department: new FormControl("", Validators.required)
  });

  onSubmit() {
    // EventEmitter with form value
    const formData = this.createUserForm.value;

    this.http.post("http://localhost:8081/users/", formData).subscribe(
      data => {
        console.warn("Enregistrement réussi");
      },
      error => {
        console.warn("Enregistrement impossible");
      }
    );

    console.warn("Valeurs du formulaire = ");
    const formKeys = Object.keys(this.createUserForm.value);
    formKeys.forEach(k => {
      console.log(k + " : " + this.createUserForm.get(k).value);
    });
  }
}
