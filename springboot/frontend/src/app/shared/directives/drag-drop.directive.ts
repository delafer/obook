import {Directive, EventEmitter, HostBinding, HostListener, Input, Output,} from '@angular/core';

export const bckground = '#ffffff';

@Directive({
  selector: 'div[appDragDrop]',
})
export class DragDropDirective {
  @Input() allowedTypes: string = '';

  @Output() onFileDropped = new EventEmitter<any>();

  @HostBinding('style.background-color') private background = bckground;
  @HostBinding('style.opacity') private opacity = '1';

  private allowed: string[];

  //Dragover listener
  @HostListener('dragover', ['$event']) onDragOver(evt: any) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = '#9ecbec';
    this.opacity = '0.8';
  }

  @HostListener('dragenter', ['$event']) onDragEnter(event: any) {
    event.preventDefault();
    event.stopPropagation();
    this.background = '#9ecbec';
    this.opacity = '0.8';
  }

  //Dragleave listener
  @HostListener('dragleave', ['$event']) public onDragLeave(evt: any) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = bckground;
    this.opacity = '1';
  }

  //Drop listener
  @HostListener('drop', ['$event']) public ondrop(evt: any) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = bckground;
    this.opacity = '1';

    // this.allowed = this.allowedTypes.split(',').map(r => r.toLowerCase());
    // let files = Array.from(evt.dataTransfer.files).filter((element: File) => this.allowed.includes(this.getExtension(element)));

    let files = evt.dataTransfer.files;
    if (files.length > 0) {
      this.onFileDropped.emit(evt.dataTransfer);
    }
  }
  //
  // getExtension(fileName: File): string {
  //   return fileName.name.split('.').pop().toLowerCase();
  // }
}
