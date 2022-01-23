export const onlyDigts = new RegExp('^\\d+$');

export class TypeUtils {
  static isString(value: any): boolean {
    return typeof value === 'string' || value instanceof String;
  }

  // Returns if a value is really a number
  static isNumber(value: any): boolean {
    return typeof value === 'number' && isFinite(value);
  }

  // Returns if a value is a function
  static isFunction(value: any): boolean {
    return typeof value === 'function';
  }

  // Returns if a value is null
  static isNull(value: any): boolean {
    return value === null;
  }

  // Returns if a value is undefined
  static isUndefined(value: any): boolean {
    return typeof value === 'undefined';
  }

  // Returns if a value is a boolean
  static isBoolean(value: any): boolean {
    return typeof value === 'boolean';
  }

  // Returns if value is a date object
  static isDate(value: any): boolean {
    return value instanceof Date;
  }

  static isIntegerValue(value: any): boolean {
    return value ? onlyDigts.test(value) : false;
  }
}
