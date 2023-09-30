import { Component, Input, forwardRef } from '@angular/core';
import { FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';

type SelectBoxOptions = [string, string | number];

@Component({
  selector: 'app-select-filter',
  templateUrl: './select-filter.component.html',
  styleUrls: ['./select-filter.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectFilterComponent),
      multi: true,
    }
  ]
})
export class SelectFilterComponent {
  @Input() filterName?: string;
  @Input() control?: FormControl;
  @Input() options: SelectBoxOptions[] = [];

  writeValue(value: any): void {
    if (value !== this.control?.value) {
      this.control?.setValue(value);
    }
  }

  // NG_VALUE_ACCESSOR requires this methods to be implemented
  registerOnChange: (fn: any) => void = () => { };
  registerOnTouched: (fn: any) => void = () => { };

}
