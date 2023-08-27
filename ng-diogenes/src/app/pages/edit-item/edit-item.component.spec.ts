import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditItemComponent } from './edit-item.component';

describe('EditItemComponent', () => {
  let component: EditItemComponent;
  let fixture: ComponentFixture<EditItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditItemComponent]
    });
    fixture = TestBed.createComponent(EditItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
