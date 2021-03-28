import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MatDialogModule } from "@angular/material";
import { NgModule, LOCALE_ID } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { FlexLayoutModule } from '@angular/flex-layout';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr, 'fr');

import { LoginAccountComponent } from "./login-account/login-account.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CreateUserAccountComponent } from "./create-userAccount/create-userAccount.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { HttpInterceptorService } from './_services/Http.interceptor.service';
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
    { provide: LOCALE_ID, useValue: 'fr' },
    { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
