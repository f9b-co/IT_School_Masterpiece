import { FormGroup } from "@angular/forms";

export function passwordChecking(form: FormGroup) {
  if (form.get("password").value !== form.get("passwordCheck").value) {
    return { invalid: true };
  }
}
