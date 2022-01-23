import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'zeroIfNull',
})
export class ZeroIfNullPipe implements PipeTransform {
  transform(value: any, symbol: string | number = 0): any {
    return !value ? symbol : value;
  }
}
