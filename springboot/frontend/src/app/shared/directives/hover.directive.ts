import { Directive, HostBinding, HostListener } from '@angular/core';

@Directive({
  selector: '[hoverElement]',
})
export class HoverDirective {
  @HostBinding('class.hovered') isHovered = false;

  @HostListener('mouseover') onMouseOver() {
    this.isHovered = true;
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.isHovered = false;
  }
}
