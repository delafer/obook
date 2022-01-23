import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor() { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
          console.log('Error!'+JSON.stringify(err));
            if (err.status === 401) {
               console.log('Reloading page!');
               // auto logout if 401 response returned from api
                //this.authenticationService.logout();
               // location.reload(true);
            }

            const error = `${err.error.error} : ${err.error.error_description}`;

          return throwError(() => new Error(error));
        }));
    }
}
