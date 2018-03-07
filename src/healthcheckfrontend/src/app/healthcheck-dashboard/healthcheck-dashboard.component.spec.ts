import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthcheckDashboardComponent } from './healthcheck-dashboard.component';

describe('HealthcheckDashboardComponent', () => {
  let component: HealthcheckDashboardComponent;
  let fixture: ComponentFixture<HealthcheckDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthcheckDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthcheckDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
