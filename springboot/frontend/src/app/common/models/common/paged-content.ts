export class PagedContent<T> {
  items: T[];
  total: number;

  constructor(items: T[]) {
    this.items = items;
  }
}
