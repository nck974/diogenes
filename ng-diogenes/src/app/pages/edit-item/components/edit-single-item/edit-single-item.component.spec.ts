import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSingleItemComponent } from './edit-single-item.component';

describe('EditSingleItemComponent', () => {
  let component: EditSingleItemComponent;
  let fixture: ComponentFixture<EditSingleItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditSingleItemComponent]
    });
    fixture = TestBed.createComponent(EditSingleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
