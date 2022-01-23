import { Pipe, PipeTransform } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { TypeUtils } from '$common/utilities/type-utils';

export declare type EnumType = { name: string; value: string; i18n: string };

@Pipe({
  name: 'enumAsArray',
})
export class EnumAsArrayPipe extends TranslatePipe implements PipeTransform {
  /**
   *  0 - do not sort
   *  1 - traslate enum keys and sort alphabetically
   *  2 - traslate enum values and sort alphabetically
   */
  override transform(value: string, filter?: string, mode: number = 0): EnumType[] {
    let unique: any[] = [];
    let exclude: string[] = filter ? filter.split(',') : [];
    let res: EnumType[] = Object.keys(value)
      .filter((j) => !TypeUtils.isIntegerValue(j))
      .map((o: any) => ({
        name: o,
        value: value[o],
        i18n: mode > 1 ? super.transform(value[o]) : super.transform(o),
      }))
      .filter((value: any) => {
        //true -> pass
        if (unique.includes(value.i18n) || exclude.includes(value.name))
          return false;
        unique.push(value.i18n);
        return true;
      });
    if (mode > 0) {
      res = res.sort((a, b) => {
        return a.i18n.localeCompare(b.i18n);
      });
    }

    return res;
  }
}
