import { SortMethod } from '$directives/ngbd-sortable-header.directive';

export type SortDirection = 'asc' | 'desc' | '';

export class SortEvent {
  column: string;
  direction: SortDirection;
  onClient: boolean;
  sortMethod: SortMethod;

  constructor(
    column?: string,
    direction?: SortDirection,
    onClient?: boolean,
    sortType?: SortMethod
  ) {
    this.column = column;
    this.direction = direction;
    this.onClient = onClient || false;
    this.sortMethod = sortType || '';
  }
}
