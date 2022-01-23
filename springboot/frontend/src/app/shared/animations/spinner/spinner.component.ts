import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { SpinnerService } from '$shared/animations/spinner/spinner.service';

@Component({
  selector: 'spinner',
  template: ` <div *ngIf="show" class="lds-dual-ring"></div> `,
  styleUrls: ['./spinner.component.scss'],
})
export class SpinnerComponent implements OnInit, OnDestroy {
  private isShowing = false;

  @Input() name: string;
  @Output() showChange = new EventEmitter();

  constructor(private spinnerService: SpinnerService) {}

  get show(): boolean {
    return this.isShowing;
  }

  @Input()
  set show(val: boolean) {
    this.isShowing = val;
    this.showChange.emit(this.isShowing);
  }

  ngOnInit() {
    if (!this.name) throw new Error("Spinner should have a 'name' attribute.");
    this.spinnerService._register(this);
  }

  ngOnDestroy(): void {
    this.spinnerService._unregister(this);
  }
}
