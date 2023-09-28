import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventorySorterComponent } from './inventory-sorter.component';

describe('InventorySorterComponent', () => {
  let component: InventorySorterComponent;
  let fixture: ComponentFixture<InventorySorterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InventorySorterComponent]
    });
    fixture = TestBed.createComponent(InventorySorterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
