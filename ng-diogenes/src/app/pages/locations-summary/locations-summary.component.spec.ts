import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationsSummaryComponent } from './locations-summary.component';

describe('LocationsSummaryComponent', () => {
  let component: LocationsSummaryComponent;
  let fixture: ComponentFixture<LocationsSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LocationsSummaryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LocationsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
