import { Directive, EventEmitter, Input, Output } from '@angular/core';
import { SortDirection, SortEvent } from '$models/common/sort-event';

const rotate: { [key: string]: SortDirection } = {
  asc: 'desc',
  desc: 'asc',
  '': 'asc',
};
export type SortMethod = 'ignoreCase' | 'naturalSort' | 'translate' | '';

@Directive({
  selector: 'th[sortable]',
  host: {
    '[class.unset]': 'direction === ""',
    '[class.asc]': 'direction === "asc"',
    '[class.desc]': 'direction === "desc"',
    '(click)': 'rotate()',
  },
})
export class NgbdSortableHeaderDirective {
  @Input() sortable: string;
  @Input() direction: SortDirection = '';
  @Input() onClient: boolean = false;
  @Input() sortType: SortMethod;
  @Output() sort = new EventEmitter<SortEvent>();

  //constructor() { }

  rotate() {
    this.direction = rotate[this.direction];
    this.sort.emit(
      new SortEvent(this.sortable, this.direction, this.onClient, this.sortType)
    );
  }
}
