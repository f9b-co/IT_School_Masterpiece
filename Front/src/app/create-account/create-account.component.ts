import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { passwordChecking } from "../validators/passwordChecking";

@Component({
  selector: "app-create-account",
  templateUrl: "./create-account.component.html",
  styleUrls: ["./create-account.component.css"]
})
export class CreateAccountComponent implements OnInit {
  constructor(private http: HttpClient) {}

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
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|" +
              "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\W).*$|" +
              "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?\\W).*$|" +
              "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$"
          )
        ])
      ),
      passwordCheck: new FormControl("", Validators.required)
    },
    { validators: passwordChecking }
  );

  cAF = this.createAccountForm;
  fc = this.createAccountForm.controls;

  onSubmit() {
    const apiUrl = "http://localhost:8081/users/";
    const patchUrl = apiUrl + this.cAF.get("login").value + "/sp"; // + "/sp"
    const headers = new HttpHeaders()
      .set("access-control-allow-origin", "http://localhost:8081")
      .set("Content-Type", "text/html");

    // Send http request with form values to back api
    this.http
      .patch(patchUrl, this.cAF.get("password").value, { headers })
      .subscribe(
        data => {
          this.cAF.reset();
          console.warn("password changé");
        },
        error => {
          console.warn("erreur!");
        }
      );

    //
    console.warn("Valeurs du formulaire = ");
    const formKeys = Object.keys(this.cAF.value);
    formKeys.forEach(k => {
      console.log(k + " : " + this.cAF.get(k).value);
    });
  }
}
