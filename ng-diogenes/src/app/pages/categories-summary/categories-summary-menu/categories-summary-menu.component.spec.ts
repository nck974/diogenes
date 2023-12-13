import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesSummaryMenuComponent } from './categories-summary-menu.component';

describe('CategoriesSummaryMenuComponent', () => {
  let component: CategoriesSummaryMenuComponent;
  let fixture: ComponentFixture<CategoriesSummaryMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriesSummaryMenuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategoriesSummaryMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
