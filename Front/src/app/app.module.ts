import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MatDialogModule } from "@angular/material";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { FlexLayoutModule } from '@angular/flex-layout';

import { LoginAccountComponent } from "./login-account/login-account.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CreateUserAccountComponent } from "./create-userAccount/create-userAccount.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { ErrorsInterceptorService } from './_services/errors.interceptor.service';
import { JwtInterceptorService } from './_services/jwt.interceptor.service';
import { HomeComponent } from './home/home.component';
import { TopRibbonComponent } from './top-ribbon/top-ribbon.component';
import { MonthlyActivityComponent } from './monthlyActivity/monthlyActivity.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CreateUserAccountComponent,
    LoginAccountComponent,
    HomeComponent,
    TopRibbonComponent,
    MonthlyActivityComponent
  ],
  imports: [
    BrowserModule,
    MatDialogModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FlexLayoutModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: ErrorsInterceptorService, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
