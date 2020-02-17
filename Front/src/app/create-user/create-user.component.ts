import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Component({
  selector: "app-create-user",
  templateUrl: "./create-user.component.html",
  styleUrls: ["./create-user.component.css"]
})
export class CreateUserComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit() {}

  passGenerator(len) {
    const alphaUpper = [..."ABCDEFGHIJKLMNOPQRSTUVWXYZ"];
    const alphaLower = [..."abcdefghijklmnopqrstuvwxyz"];
    const specialChars = [..."~!@#$%^&*()_+-=[]\\{}|;:',./<>?"];
    const numbers = [..."0123456789"];
    const base = [...alphaUpper, ...numbers, ...alphaLower, ...specialChars];
    const randPassword = [...Array(len)]
      .map(i => base[(Math.random() * base.length) | 0])
      .join("");
    return "R@nd" + randPassword;
  }

  createUserForm = new FormGroup({
    login: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(7),
        Validators.maxLength(7)
      ])
    ),
    firstName: new FormControl(
      "",
      Validators.compose([Validators.required, Validators.maxLength(64)])
    ),
    lastName: new FormControl(
      "",
      Validators.compose([Validators.required, Validators.maxLength(64)])
    ),
    email: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.email,
        Validators.maxLength(64)
      ])
    ),
    department: new FormControl(
      "",
      Validators.compose([Validators.required, Validators.maxLength(64)])
    ),
    noAccount: new FormControl(true),
    password: new FormControl(this.passGenerator(10))
  });

  onSubmit() {
    // Send http request with form values to back api
    let headers = new HttpHeaders()
      .set("access-control-allow-origin", "http://localhost:8081")
      //.set("Access-Control-Request-Method", "GET,HEAD,PUT,PATCH,POST,DELETE")
      .set("Content-Type", "application/json");

    this.http
      .post(
        "http://localhost:8081/users/",
        JSON.stringify(this.createUserForm.value),
        { headers }
      )
      .subscribe(
        data => {
          console.warn("Enregistrement réussi");
        },
        error => {
          console.warn("Enregistrement impossible");
        }
      );
  }
}
