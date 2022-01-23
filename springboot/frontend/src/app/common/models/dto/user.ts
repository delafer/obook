import { JsonObject } from 'json2typescript';

@JsonObject('UserDTO')
export class UserDTO {
  username: string;
  firstName: string;
  lastName: string;
}
