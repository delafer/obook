import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'app-messagebox',
  templateUrl: './messagebox.component.html',
  styleUrls: ['./messagebox.component.scss'],
})
export class MessageboxComponent {
  @Input() name: string;
  @Input() text: string;
  constructor(public activeModal: NgbActiveModal, private router: Router) {}

  toSettings() {
    this.router.navigate(['/home/settings']);
    this.activeModal.close(true);
  }
}
