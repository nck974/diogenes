import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationsSummaryMenuComponent } from './locations-summary-menu.component';

describe('LocationsSummaryMenuComponent', () => {
  let component: LocationsSummaryMenuComponent;
  let fixture: ComponentFixture<LocationsSummaryMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LocationsSummaryMenuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LocationsSummaryMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
