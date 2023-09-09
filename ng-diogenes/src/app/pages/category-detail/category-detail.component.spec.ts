import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDetailComponent } from './category-detail.component';

describe('CategoryDetailComponent', () => {
  let component: CategoryDetailComponent;
  let fixture: ComponentFixture<CategoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryDetailComponent]
    });
    fixture = TestBed.createComponent(CategoryDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
