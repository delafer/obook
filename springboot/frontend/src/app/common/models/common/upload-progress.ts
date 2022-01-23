import { Operation } from '$models/enums/operation.enum';

export interface UploadProgress {
  type: Operation;
  uuid: string;
}
