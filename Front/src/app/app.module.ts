import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HttpClientModule } from '@angular/common/http';

import { ReactiveFormsModule } from "@angular/forms";
import { CreateAccountComponent } from "./create-account/create-account.component";
import { LoginAccountComponent } from "./login-account/login-account.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CreateUserComponent } from './create-user/create-user.component';
import { NavbarComponent } from './navbar/navbar.component';

@NgModule({
  declarations: [AppComponent, CreateAccountComponent, LoginAccountComponent, CreateUserComponent, NavbarComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
