import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardMenuComponent } from './dashboard-menu.component';

describe('DashboardMenuComponent', () => {
  let component: DashboardMenuComponent;
  let fixture: ComponentFixture<DashboardMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardMenuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashboardMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
