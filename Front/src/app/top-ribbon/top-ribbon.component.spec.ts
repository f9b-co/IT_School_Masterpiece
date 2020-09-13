import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopRibbonComponent } from './top-ribbon.component';

describe('TopRibbonComponent', () => {
  let component: TopRibbonComponent;
  let fixture: ComponentFixture<TopRibbonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopRibbonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopRibbonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
