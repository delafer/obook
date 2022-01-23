import {
  AbstractControl,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';

function isEmptyInputValue(value: any): boolean {
  // we don't check for string here so it also works with arrays
  return value == null || value.length === 0;
}

export const checkValidator = Validators.pattern('true');

export class PnwValidators {
  static checked(): ValidatorFn | null {
    return checkValidator;
  }

  static greaterThan(min: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (isEmptyInputValue(control.value) || isEmptyInputValue(min)) {
        return null; // don't validate empty values to allow optional controls
      }
      const value = parseFloat(control.value);
      // Controls with NaN values after parsing should be treated as not having a
      // minimum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
      return !isNaN(value) && value <= min
        ? { min: { min: 1 + min, actual: control.value } }
        : null;
    };
  }

  /**
   * Validator that requires controls to have a value less than a number.
   */
  static smallerThan(max: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (isEmptyInputValue(control.value) || isEmptyInputValue(max)) {
        return null; // don't validate empty values to allow optional controls
      }
      const value = parseFloat(control.value);
      // Controls with NaN values after parsing should be treated as not having a
      // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-max
      return !isNaN(value) && value >= max
        ? { max: { max: -1 + max, actual: control.value } }
        : null;
    };
  }

  static email(): ValidatorFn {
    return Validators.pattern(
      '^[A-Za-z0-9._%+äüöÄÜÖß-]+@[A-Za-z0-9.äüöÄÜÖß-]+\\.[A-Za-z]{2,4}$'
    );
  }

  static phone(): ValidatorFn {
    return Validators.pattern('^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]*$');
  }

  static currency(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (isEmptyInputValue(control.value)) {
        return null; // don't validate empty values to allow optional controls
      }
      const value = parseFloat(control.value);
      // Controls with NaN values after parsing should be treated as not having a
      // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-max
      return !value.toString().match(/^[0-9]+(\.?[0-9]{1,2})?$/) ||
        isNaN(+value)
        ? { pattern: { required: 'numeric value', actual: control.value } }
        : null;
    };
  }

  static iban(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (isEmptyInputValue(control.value)) {
        return null; // don't validate empty values to allow optional controls
      }
      return isValidIBANChecksum(control.value)
        ? null
        : { 'invalid iban: ': { value: control.value } };
    };
  }

  static fromToDate(
    fromDateField: string,
    toDateField: string,
    errorName: string = 'fromToDate'
  ): ValidatorFn {
    return (formGroup: AbstractControl): { [key: string]: boolean } | null => {
      const fromDate = formGroup.get(fromDateField).value;
      const toDate = formGroup.get(toDateField).value;
      // Ausing the fromDate and toDate are numbers. In not convert them first after null check
      if (fromDate !== null && toDate !== null && fromDate > toDate) {
        return { [errorName]: true };
      }
      return null;
    };
  }

  static bic(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (isEmptyInputValue(control.value)) {
        return null; // don't validate empty values to allow optional controls
      }

      var reg = new RegExp('^[a-zA-Z]{6}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?$', '');
      let bic = control.value.replace(/\s/g, '');
      return reg.test(bic) && (bic.length === 11 || bic.length === 8)
        ? null
        : { 'invalid bic: ': { value: control.value } };
    };
  }
}

/**
 * makes the field required if the predicate function returns true
 */
export function requiredIfValidator(
  predicate: any,
  validator: ValidatorFn = Validators.required
) {
  // console.log(`2.atLeastOne: ${JSON.stringify(predicate)}`);
  return (formControl: any) => {
    if (!formControl.parent) {
      return null;
    }
    if (predicate()) {
      return validator(formControl);
    }
    return null;
  };
}

export function isValidIBANChecksum(ibanArg: string) {
  let iban = ibanArg.replace(/\s/g, '');
  let providedChecksum = parseInt(iban.slice(2, 4), 10);
  let temp = iban.slice(3) + iban.slice(0, 2) + '00';
  let validationString = '';
  for (var n = 1; n < temp.length; n++) {
    var c = temp.charCodeAt(n);
    if (c >= 65) {
      validationString += (c - 55).toString();
    } else {
      validationString += temp[n];
    }
  }
  while (validationString.length > 2) {
    let part = validationString.slice(0, 6);
    validationString =
      (parseInt(part, 10) % 97).toString() +
      validationString.slice(part.length);
  }
  let rest = parseInt(validationString, 10) % 97;
  return 98 - rest === providedChecksum;
}
