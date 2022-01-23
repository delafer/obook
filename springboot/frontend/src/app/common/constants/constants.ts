export class Constants {
  static readonly MAX_FETCH_ENTRIES: number = 100;
  static readonly ENTRIES_PRO_PAGE: number = 10;

  static readonly DEFAULT_LOCALE: string = 'en-US';
  static readonly LANG_DEFAULT: string = Constants.DEFAULT_LOCALE.slice(0, 2);
  static readonly AVAILABLE_LANGS: string[] = ['de', 'en', 'ru'];
}
