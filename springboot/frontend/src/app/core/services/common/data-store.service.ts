import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DataStoreService {
  constructor() {}

  private getKey(key: string): string {
    return `${key}`;
  }

  saveData(key: string, data: any): void {
    this.checkValidArguments(key);
    try {
      const fullKey = this.getKey(key);
      if (data) {
        localStorage.setItem(fullKey, JSON.stringify(data));
      } else {
        localStorage.removeItem(fullKey);
      }
    } catch (e) {
      console.error('Error saving to localStorage', e);
    }
  }

  loadData(key: string): any {
    this.checkValidArguments(key);
    try {
      return JSON.parse(localStorage.getItem(this.getKey(key)));
    } catch (e) {
      console.error('Error getting data from localStorage', e);
      return null;
    }
  }

  removeData(key: string): void {
    this.checkValidArguments(key);
    try {
      localStorage.removeItem(this.getKey(key));
    } catch (e) {
      console.error('Error getting data from localStorage', e);
      return null;
    }
  }

  private checkValidArguments(key: string) {
    if (!key) throw new Error('Key ist not set');
  }
}
