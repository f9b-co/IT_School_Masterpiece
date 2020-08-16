import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Component({
  selector: "app-create-userAccount",
  templateUrl: "./create-userAccount.component.html",
  styleUrls: ["./create-userAccount.component.css"],
})
export class CreateUserAccountComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit() {}

  createUserAccountForm = new FormGroup({
    username: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(7),
        Validators.maxLength(7),
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
        Validators.maxLength(128),
      ])
    ),
    department: new FormControl(
      "",
      Validators.compose([Validators.required, Validators.maxLength(64)])
    ),
  });
  cUF = this.createUserAccountForm;
  fc = this.createUserAccountForm.controls;

  onSubmit() {
    const apiUrl = "http://localhost:8081/users/";
    let headers = new HttpHeaders()
      .set("access-control-allow-origin", "http://localhost:8081")
      .set("Content-Type", "application/json");

    // Send http request with form values to back api
    this.http
      .post(apiUrl, JSON.stringify(this.cUF.value), { headers })
      .subscribe(
        (data) => {
          this.cUF.reset();
          console.warn("Enregistrement réussi");
        },
        (error) => {
          console.warn("Enregistrement impossible");
        }
      );
  }
}
