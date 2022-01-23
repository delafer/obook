import { Injectable } from '@angular/core';
import { ActiveToast, IndividualConfig, ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class ToasterService {
  private disableToCfg: Partial<IndividualConfig> = {
    disableTimeOut: true,
  };

  private warningCfg: Partial<IndividualConfig> = {
    // timeOut: 60000
    disableTimeOut: true,
  };

  private successCfg: Partial<IndividualConfig> = {
    // timeOut: 20000
    disableTimeOut: true,
  };

  constructor(private toastrService: ToastrService) {}

  /** show toast */
  show(
    message?: string,
    title?: string,
    override?: Partial<IndividualConfig>,
    type?: string
  ): ActiveToast<any> {
    return this.toastrService.show(message, title, override, type);
  }
  /** show successful toast */
  success(
    message?: string,
    title?: string,
    override?: Partial<IndividualConfig>
  ): ActiveToast<any> {
    return this.toastrService.success(
      message,
      title,
      override ? override : this.successCfg
    );
  }
  /** show error toast */
  // error(message?: string, title?: string, override?: Partial<IndividualConfig>): ActiveToast<any> {
  //   return this.toastrService.error(message, title, override ? override : this.disableToCfg);
  // }
  /** show info toast */
  info(
    message?: string,
    title?: string,
    override?: Partial<IndividualConfig>
  ): ActiveToast<any> {
    return this.toastrService.info(message, title, override);
  }
  /** show warning toast */
  warning(
    message?: string,
    title?: string,
    override?: Partial<IndividualConfig>
  ): ActiveToast<any> {
    return this.toastrService.warning(
      message,
      title,
      override ? override : this.warningCfg
    );
  }
  /**
   * Remove all or a single toast by id
   */
  clear(toastId?: number): void {
    return this.clear(toastId);
  }
  /**
   * Remove and destroy a single toast by id
   */
  remove(toastId: number): boolean {
    return this.remove(toastId);
  }

  error(
    message?: string,
    title?: string,
    override?: Partial<IndividualConfig>
  ): ActiveToast<any> {
    return this.toastrService.error(message, title, {
      enableHtml: true,
    });
  }
}
