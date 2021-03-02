import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-ribbon',
  templateUrl: './top-ribbon.component.html',
  styleUrls: ['./top-ribbon.component.css']
})
export class TopRibbonComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  logout() {
    window.sessionStorage.removeItem("access_Token");
  }

}
