import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function negativeValueValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    return value < 0 ? { negativeValue: { value: value } } : null;
  };
}
