import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Constants} from "$common/constants/constants";
import {environment} from "$environment/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'BookScanner';

  constructor(
    translate: TranslateService,
  ) {
    translate.addLangs(Constants.AVAILABLE_LANGS);
    translate.setDefaultLang(Constants.LANG_DEFAULT);
    translate.use('en');
  }

  ngOnInit(): void {
    console.log('Application started');
    console.log('Server API Url: ' + environment.restUrl);
  }

}
