import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditItemImageComponent } from './edit-item-image.component';

describe('EditItemImageComponent', () => {
  let component: EditItemImageComponent;
  let fixture: ComponentFixture<EditItemImageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditItemImageComponent]
    });
    fixture = TestBed.createComponent(EditItemImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
