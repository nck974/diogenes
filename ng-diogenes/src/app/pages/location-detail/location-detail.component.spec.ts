import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationDetailComponent } from './location-detail.component';

describe('LocationDetailComponent', () => {
  let component: LocationDetailComponent;
  let fixture: ComponentFixture<LocationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LocationDetailComponent]
    });
    fixture = TestBed.createComponent(LocationDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
