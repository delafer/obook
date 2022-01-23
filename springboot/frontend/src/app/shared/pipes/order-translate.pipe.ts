import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Pipe({
  name: 'orderTranslate',
})
export class OrderTranslatePipe implements PipeTransform {
  constructor(private translate: TranslateService) {}

  transform(array: Array<any>): Array<any> {
    array.sort((a: any, b: any) => {
      if (this.translate.instant(a.value) < this.translate.instant(b.value))
        return -1;
      else if (
        this.translate.instant(a.value) > this.translate.instant(b.value)
      )
        return 1;
      else return 0;
    });
    return array;
  }
}
