import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileName',
})
export class FileNamePipe implements PipeTransform {
  transform(value: any, len: number = 30): any {
    return this.getfile(value, len);
  }

  shorten(str: string, reduce: number, atEnd: number) {
    let dots = atEnd > 0 ? '..' : '.';
    let bis: number = str.length - reduce - dots.length - atEnd;
    if (bis - atEnd < 2) {
      bis += atEnd;
      atEnd = 0;
    }
    return (
      str.substring(0, bis) + dots + (atEnd !== 0 ? str.slice(-atEnd) : '')
    );
  }

  getfile(data: string, max: number): string {
    if (!data || data.length == 0) return data;
    let idx = data.lastIndexOf('.');
    if (idx < 0) idx = data.length;

    let toreduce: number = data.length - max;
    // console.log(`toreduce: ${toreduce} and idx: ${idx}`);
    if (toreduce <= 0) return data;
    let maxreduce: number = idx - 4;
    // console.log(`maxReduce: ${maxreduce}`);
    if (maxreduce <= 0) return data;
    let reduce: number = Math.min(toreduce, maxreduce);
    // console.log(`reduce: ${reduce}`);
    let name = data.substring(0, idx);

    let atEnd = maxreduce - reduce;
    // console.log(`atEnd: ${atEnd}`);
    if (atEnd === 1) atEnd = 0;
    else if (atEnd > 2) atEnd = 2;

    let newName = this.shorten(name, reduce, atEnd) + data.slice(idx);

    return newName;
  }
}
