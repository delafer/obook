import {Observable, of} from 'rxjs';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ApiErrorDTO} from "$models/dto/error/api-error";

//export declare type FnOnError = (error: HttpErrorResponse) => boolean;
export const UNKNOWN_KEY = '-1';
const SUCCESS_FIELD = 'success';

@Injectable()
export abstract class AbstractService {
  public maxResults: string = '' + 10;

  public handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      if (error.message !== 'no elements in sequence') {
        // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead
        // TODO: better job of transforming error for user consumption
        console.log(`${operation} failed: ${error.message}`);
      }

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  checkHttpResponse(response: HttpResponse<any>): ApiErrorDTO {
    return response ? this.checkResponse(response.body) : null;
  }

  checkErrorResponse(response: HttpErrorResponse): ApiErrorDTO {
    let respError: any = response.error;
    if ('apiErrorCode' in respError || 'message' in respError) {
      return respError;
    }
    return null;
  }

  checkResponse(response: any): ApiErrorDTO {
    if (SUCCESS_FIELD in response && response[SUCCESS_FIELD] === false) {
      let error: ApiErrorDTO = response['error'];
      return error;
    }
    return null;
  }

  extractErrorKey(error: ApiErrorDTO, defError?: string): string {
    if (error) {
      return `${error.apiErrorCode}`;
    }
    return null;
  }
}
