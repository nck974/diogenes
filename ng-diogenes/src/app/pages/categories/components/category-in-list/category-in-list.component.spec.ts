import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryInListComponent } from './category-in-list.component';

describe('CategoryInListComponent', () => {
  let component: CategoryInListComponent;
  let fixture: ComponentFixture<CategoryInListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryInListComponent]
    });
    fixture = TestBed.createComponent(CategoryInListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
