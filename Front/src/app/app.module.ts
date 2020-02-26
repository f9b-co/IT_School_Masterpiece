import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MatDialogModule } from "@angular/material";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";

import { CreateAccountComponent } from "./create-account/create-account.component";
import { LoginAccountComponent } from "./login-account/login-account.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CreateUserComponent } from "./create-user/create-user.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { InterceptorService } from './services/interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    CreateAccountComponent,
    LoginAccountComponent,
    CreateUserComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    MatDialogModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
