import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {LoaderComponent} from './loader/loader.component';
import {NgbDropdownModule, NgbProgressbarModule,} from '@ng-bootstrap/ng-bootstrap';
import {FileNamePipe} from '$pipes/file-name.pipe';
import {DragDropDirective} from '$directives/drag-drop.directive';
import {FileSizePipe} from '$pipes/file-size.pipe';
import {EnumAsArrayPipe} from '$pipes/enum-as-array.pipe';
import {TranslateModule} from '@ngx-translate/core';
import {StripPipe} from '$shared/pipes';
import {RomanizePipe} from '$pipes/romanize.pipe';

@NgModule({
  imports: [
    CommonModule,
    NgbProgressbarModule,
    NgbDropdownModule,
    TranslateModule,
  ],
  declarations: [
    LoaderComponent,
    FileNamePipe,
    FileSizePipe,
    DragDropDirective,
    EnumAsArrayPipe,
    StripPipe,
    RomanizePipe,
  ],
  exports: [
    LoaderComponent,
    FileNamePipe,
    FileSizePipe,
    DragDropDirective,
    EnumAsArrayPipe,
    StripPipe,
    RomanizePipe,
  ],
})
export class ComponentsModule {}
