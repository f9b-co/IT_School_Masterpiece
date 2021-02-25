import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { CreateUserAccountComponent } from "./create-userAccount/create-userAccount.component";
import { LoginAccountComponent } from "./login-account/login-account.component";
import { Monthtly-ActivityComponent } from "./monthly-activity/monthtly-activity.component";

const routes: Routes = [
  { path: "home", component: HomeComponent },
  { path: "create", component: CreateUserAccountComponent },
  { path: "login", component: LoginAccountComponent },
  { path: "inside", component: Monthtly-ActivityComponent },
  { path: "", redirectTo: "/login", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
