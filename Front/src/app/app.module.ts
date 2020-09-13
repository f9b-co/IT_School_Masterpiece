import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MatDialogModule } from "@angular/material";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";

import { SetPasswordComponent } from "./set-Password/set-Password.component";
import { LoginAccountComponent } from "./login-account/login-account.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CreateUserAccountComponent } from "./create-userAccount/create-userAccount.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { InterceptorService } from './services/interceptor.service';
import { HomeComponent } from './home/home.component';
import { TopRibbonComponent } from './top-ribbon/top-ribbon.component';
import { InsideComponent } from './inside/inside.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SetPasswordComponent,
    CreateUserAccountComponent,
    LoginAccountComponent,
    HomeComponent,
    TopRibbonComponent,
    InsideComponent
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
