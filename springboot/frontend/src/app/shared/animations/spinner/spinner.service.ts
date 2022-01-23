import { Injectable } from '@angular/core';
import { SpinnerComponent } from '$shared/animations/spinner/spinner.component';
import { Subject } from 'rxjs';
import { auditTime } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class SpinnerService {
  private events: Subject<boolean> = new Subject<boolean>();

  constructor() {
    this.events.pipe(auditTime(600)).subscribe((result) => {
      this.spinner.show = result;
    });
  }

  //private spinnerCache = new Set<SpinnerComponent>();
  private spinner: SpinnerComponent;

  _register(spinner: SpinnerComponent): void {
    // this.spinnerCache.add(spinner);
    this.spinner = spinner;
  }

  _unregister(spinnerToRemove: SpinnerComponent): void {
    // this.spinnerCache.forEach(spinner => {
    //   if (spinner === spinnerToRemove) {
    //     this.spinnerCache.delete(spinner);
    //   }
    // });
    this.spinner = null;
  }

  _unregisterAll(): void {
    //this.spinnerCache.clear();
    this.spinner = null;
  }

  showGlobal(): void {
    if (this.spinner) {
      //this.spinner.show = true;
      this.events.next(true);
    }
  }

  hideGlobal(): void {
    if (this.spinner) {
      //this.spinner.show = false;
      this.events.next(false);
    }
  }

  show(spinnerName: string): void {
    // this.spinnerCache.forEach(spinner => {
    //   if (spinner.name === spinnerName) {
    //     spinner.show = true;
    //   }
    // });
  }

  hide(spinnerName: string): void {
    // this.spinnerCache.forEach(spinner => {
    //   if (spinner.name === spinnerName) {
    //     spinner.show = false;
    //   }
    // });
  }

  showAll(): void {
    // this.spinnerCache.forEach(spinner => spinner.show = true);
  }

  hideAll(): void {
    // this.spinnerCache.forEach(spinner => spinner.show = false);
  }

  isShowing(spinnerName: string): boolean | undefined {
    // let showing = undefined;
    // this.spinnerCache.forEach(spinner => {
    //   if (spinner.name === spinnerName) {
    //     showing = spinner.show;
    //   }
    // });
    // return showing;
    return this.spinner.show;
  }
}
