import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthlyActivityComponent } from './monthlyActivity.component';

describe('InsideComponent', () => {
  let component: MonthlyActivityComponent;
  let fixture: ComponentFixture<MonthlyActivityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonthlyActivityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonthlyActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
