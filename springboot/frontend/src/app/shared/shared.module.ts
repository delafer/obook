import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {Nl2brPipe} from '$shared/pipes';
import {NgbdSortableHeaderDirective} from '$directives/ngbd-sortable-header.directive';
import {HoverDirective} from '$directives/hover.directive';
import {SpinnerComponent} from './animations/spinner/spinner.component';
import {ZeroIfNullPipe} from '$pipes/zero-if-null.pipe';
import {NgbdFilterableDirective} from '$directives/ngbd-filterable.directive';
import {AutofocusDirective} from '$directives/autofocus.directive';
import {OrderTranslatePipe} from '$pipes/order-translate.pipe';
import {NgbProgressbarModule} from '@ng-bootstrap/ng-bootstrap';
import {ComponentsModule} from '$shared/components';
import {InfoboxComponent} from './components/infobox/infobox.component';
import {MessageboxComponent} from './components/messagebox/messagebox.component';

@NgModule({
  imports: [CommonModule, NgbProgressbarModule, ComponentsModule],
  declarations: [
    Nl2brPipe,
    ZeroIfNullPipe,
    NgbdSortableHeaderDirective,
    HoverDirective,
    SpinnerComponent,
    OrderTranslatePipe,
    NgbdFilterableDirective,
    AutofocusDirective,
    InfoboxComponent,
    MessageboxComponent
  ],
  exports: [
    TranslateModule,
    Nl2brPipe,
    ZeroIfNullPipe,
    OrderTranslatePipe,
    NgbdSortableHeaderDirective,
    HoverDirective,
    SpinnerComponent,
    AutofocusDirective,
    ComponentsModule,
    MessageboxComponent
  ],
})
export class SharedModule {}
