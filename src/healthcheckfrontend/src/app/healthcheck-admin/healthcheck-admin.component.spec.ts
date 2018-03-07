import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthcheckAdminComponent } from './healthcheck-admin.component';

describe('HealthcheckAdminComponent', () => {
  let component: HealthcheckAdminComponent;
  let fixture: ComponentFixture<HealthcheckAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthcheckAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthcheckAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
