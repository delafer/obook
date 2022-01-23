import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'strip',
})
export class StripPipe implements PipeTransform {
  transform(value: any, len: number = 255): any {
    return StripPipe.shorten(value, len);
  }

  public static shorten(value: any, len: number): any {
    if (value !== void 0 && value && value.length > len) {
      //return value.replace(/\n/g, '<br>');
      return value.substring(0, len) + '...';
    }
    return value;
  }
}
