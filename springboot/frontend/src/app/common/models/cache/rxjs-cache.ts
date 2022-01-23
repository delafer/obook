import { Observable, Subject } from 'rxjs';

export class RxJsCache<T> {
  reloadItems$;
  cacheItems$: Observable<T>;

  constructor() {
    this.reloadItems$ = new Subject<void>();
  }

  forceReload(): void {
    this.reloadItems$.next();
    this.cacheItems$ = null;
  }
}
