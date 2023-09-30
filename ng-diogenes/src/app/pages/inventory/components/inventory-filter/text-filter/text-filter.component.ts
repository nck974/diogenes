import { Component, Input, forwardRef } from '@angular/core';
import { FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';
import { ValidationMessages } from 'src/app/models/ValidationMessages';


@Component({
  selector: 'app-text-filter',
  templateUrl: './text-filter.component.html',
  styleUrls: ['./text-filter.component.scss', '../inventory-filter.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextFilterComponent),
      multi: true,
    }
  ]
})
export class TextFilterComponent {
  @Input() filterName?: string;
  @Input() control?: FormControl;

  validationMessages: ValidationMessages = {
    isNotANumber: 'Enter a valid number',
  };

  writeValue(value: any): void {
    if (value !== this.control?.value) {
      this.control?.setValue(value);
    }
  }

  // NG_VALUE_ACCESSOR requires this methods to be implemented
  registerOnChange: (fn: any) => void = () => { };
  registerOnTouched: (fn: any) => void = () => { };

  getErrorMessage(key: string): string {
    return this.validationMessages[key as keyof ValidationMessages];
  }
}
