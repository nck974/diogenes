import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBulkComponent } from './edit-bulk.component';

describe('EditBulkComponent', () => {
  let component: EditBulkComponent;
  let fixture: ComponentFixture<EditBulkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditBulkComponent]
    });
    fixture = TestBed.createComponent(EditBulkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
