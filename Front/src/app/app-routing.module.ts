import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { CreateUserAccountComponent } from "./create-userAccount/create-userAccount.component";
import { LoginAccountComponent } from "./login-account/login-account.component";
import { MonthlyActivityComponent } from "./monthlyActivity/monthlyActivity.component";
import { AuthGuard } from './_helpers/auth.guard';
import { Role } from './_models/role';

const routes: Routes = [
  { path: "home", component: HomeComponent },
  { path: "create", component: CreateUserAccountComponent },
  { path: "login", component: LoginAccountComponent },
  {
    path: "monthlyActivity", component: MonthlyActivityComponent, canActivate: [AuthGuard],
    data: { roles: [Role.Admin, Role.Manager, Role.User] }
  },
  { path: "", redirectTo: "/home", pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
