/*copied from https://github.com/ng-bootstrap/ng-bootstrap/issues/2072*/
/**
 * Dateformat DD.MM.YYYY
 */
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {Injectable} from '@angular/core';

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
  parse(value: string): NgbDateStruct {
    if (value) {
      const dateParts = value.trim().split('.');
      if (dateParts.length === 1 && this.isNumber(dateParts[0])) {
        return {day: this.toInteger(dateParts[0]), month: null, year: null};
      } else if (dateParts.length === 2 && this.isNumber(dateParts[0]) && this.isNumber(dateParts[1])) {
        return {day: this.toInteger(dateParts[0]), month: this.toInteger(dateParts[1]), year: null};
      } else if (dateParts.length === 3 && this.isNumber(dateParts[0]) && this.isNumber(dateParts[1]) && this.isNumber(dateParts[2])) {
        return {
          day: this.toInteger(dateParts[0]),
          month: this.toInteger(dateParts[1]),
          year: this.toInteger(dateParts[2])
        };
      }
    }
    return null;
  }

  /* tslint:disable */
  format(date: NgbDateStruct): string {
    return date ?
      `${this.isNumber(date.day) ? this.padNumber(date.day) : ''}.${this.isNumber(date.month) ? this.padNumber(date.month) : ''}.${date.year}` : '';
  }

  /* tslint:enable */

  toInteger(value: any): number {
    return parseInt(`${value}`, 10);
  }

  toString(value: any): string {
    return (value !== undefined && value !== null) ? `${value}` : '';
  }

  getValueInRange(value: number, max: number, min = 0): number {
    return Math.max(Math.min(value, max), min);
  }

  isString(value: any): value is string {
    return typeof value === 'string';
  }

  isNumber(value: any): value is number {
    return !isNaN(this.toInteger(value));
  }

  isInteger(value: any): value is number {
    return typeof value === 'number' && isFinite(value) && Math.floor(value) === value;
  }

  isDefined(value: any): boolean {
    return value !== undefined && value !== null;
  }

  padNumber(value: number) {
    if (this.isNumber(value)) {
      return `0${value}`.slice(-2);
    } else {
      return '';
    }
  }

  regExpEscape(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\$&');
  }

  hasClassName(element: any, className: string): boolean {
    return element && element.className && element.className.split &&
      element.className.split(/\s+/).indexOf(className) >= 0;
  }
}
