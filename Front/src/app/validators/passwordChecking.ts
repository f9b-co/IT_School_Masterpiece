import { FormGroup } from "@angular/forms";

export function passwordChecking(form: FormGroup) {
  let invalid = true;
  if (form.get("password").value !== form.get("passwordCheck").value) {
    form.get("passwordCheck").setErrors({ passwordChecking: true });
  } else {
    form.get("passwordCheck").setErrors({ passwordChecking: false });
    if (form.get("password").errors == null) {
      form.setErrors(null);
      invalid = false;
    }
  }
  return { invalid: invalid };
}
