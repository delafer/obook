import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-infobox',
  templateUrl: './infobox.component.html',
  styleUrls: ['./infobox.component.scss'],
})
export class InfoboxComponent {
  @Input() name: string;
  @Input() text1: string;
  @Input() text2: string;
  @Input() text3: string;
  constructor(public activeModal: NgbActiveModal) {}

  setAbort() {
    this.activeModal.close(false);
  }

  setSend() {
    this.activeModal.close(true);
  }
}
