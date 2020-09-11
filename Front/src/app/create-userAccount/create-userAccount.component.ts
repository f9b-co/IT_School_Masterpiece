import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { passwordChecking } from "../validators/passwordChecking";

@Component({
  selector: "app-create-userAccount",
  templateUrl: "./create-userAccount.component.html",
  styleUrls: ["./create-userAccount.component.css"],
})
export class CreateUserAccountComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit() {}

  createUserAccountForm = new FormGroup(
    {
      username: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(7),
          Validators.maxLength(7),
        ])
      ),
      password: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20),
          Validators.pattern(
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|" +
              "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\W).*$|" +
              "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?\\W).*$|" +
              "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$"
          ),
        ])
      ),
      passwordCheck: new FormControl("", Validators.required),
      firstName: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(64),
        ])
      ),
      lastName: new FormControl(
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(64),
        ])
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
    },
    { validators: passwordChecking }
  );
  cUF = this.createUserAccountForm;
  fc = this.createUserAccountForm.controls;

  onSubmit() {
    const apiUrl = "http://localhost:8081/api/employees/";
    const headers = new HttpHeaders().set("Content-Type", "application/json");
    const dataToSend = JSON.stringify(this.cUF.value);
    Object.keys(dataToSend).forEach(function (key) {
      if (dataToSend[key] == "passwordCheck") delete dataToSend[key];
    });
    console.log(dataToSend);

    // Send http request with form values to back api
    this.http.post(apiUrl, dataToSend, { headers }).subscribe(
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
