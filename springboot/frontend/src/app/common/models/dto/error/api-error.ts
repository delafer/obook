import {ApiErrorCode} from './api-errorcode';

export class ApiErrorDTO {

  apiErrorCode: ApiErrorCode;
  message: string;
  detailedMessage?: string;

  constructor(apiErrorCode?: ApiErrorCode, message?: string, detailedMessage?: string) {

    this.apiErrorCode = apiErrorCode;
    this.message = message;
    this.detailedMessage = detailedMessage;
   }

}
