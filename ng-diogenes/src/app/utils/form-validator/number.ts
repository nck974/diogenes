import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function isNumberValidator(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        const isNumber = !isNaN(control.value as number);
        return isNumber ? null : { isNotANumber: { value: control.value } };
    };
}