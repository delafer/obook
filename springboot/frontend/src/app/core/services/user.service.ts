import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserDTO} from '$models/dto/user';
import {Observable} from 'rxjs';
import {Urls} from '$environment/urls';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  getAllAnalysts(): Observable<UserDTO[]> {
    const url = `${Urls.restUrl}/risks/users/ka`;
    return this.http.get<UserDTO[]>(url);
  }

  getAllContracts(): Observable<UserDTO[]> {
    const url = `${Urls.restUrl}/risks/users/contracts`;
    return this.http.get<UserDTO[]>(url);
  }

  getContract(contractId: string): Observable<UserDTO> {
    const url = `${Urls.restUrl}/contracts/${contractId}`;
    return this.http.get<UserDTO>(url);
  }
}
