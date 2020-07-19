import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { CreateUserAccountComponent } from "./create-userAccount.component";

describe("CreateUserComponent", () => {
  let component: CreateUserAccountComponent;
  let fixture: ComponentFixture<CreateUserAccountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateUserAccountComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateUserAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
