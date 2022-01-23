import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from '$features/root/app.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {NgxWebstorageModule} from "ngx-webstorage";
import {ErrorInterceptor} from "$core/interceptors";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {SharedModule} from "$app/shared";
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {Urls} from "$environment/urls";
import {NgxMaskModule} from "ngx-mask";
import { HomeModule } from './features/module.home/home.module';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(
    httpClient,
    Urls.frontendUrl + '/assets/i18n/',
    '-lang.json'
  );
}


@NgModule({
  /*
  List of components, directives, and pipes that belong to this module.
  */
  declarations: [
    AppComponent
  ],
  /*
  List of modules to import into this module. Everything from the
  imported modules is available to declarations of this module.
  */
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    SharedModule,
    NgxMaskModule.forRoot(),
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    HomeModule
  ],
  /*
  List of components, directives, and pipes visible to modules that import
  this module.
  */
  exports: [
  ],
  /*
  List of dependency injection providers (e.G. services, interceptors, etc)
  visible both to the contents of this module and to importers of this module.
  */
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
  ],
  /*
  List of components to bootstrap when this module is bootstrapped.
  */
  bootstrap: [AppComponent]
})
export class AppModule { }
