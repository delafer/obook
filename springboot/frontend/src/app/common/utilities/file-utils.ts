export class FileUtils {
  static showOrSaveFile(dataBlob: Blob, fileTitle: string, fileType: FileType) {
    // It is necessary to create a new blob object with mime-type explicitly set
    // otherwise only Chrome works like it should
    let newBlob = new Blob([dataBlob], { type: FileUtils.mimeType(fileType) });
    let fileName = `${fileTitle}.${fileType.toString()}`;
    // IE doesn't allow using a blob object directly as link href
    // instead it is necessary to use msSaveOrOpenBlob
    // @ts-ignore
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      // @ts-ignore
      window.navigator.msSaveOrOpenBlob(newBlob, fileName);
      return;
    }

    // For other browsers:
    // Create a link pointing to the ObjectURL containing the blob.
    const data = window.URL.createObjectURL(newBlob);

    let link = document.createElement('a');
    link.href = data;
    link.download = fileName;
    // this is necessary as link.click() does not work on the latest firefox
    link.dispatchEvent(
      new MouseEvent('click', { bubbles: true, cancelable: true, view: window })
    );

    setTimeout(function () {
      // For Firefox it is necessary to delay revoking the ObjectURL
      window.URL.revokeObjectURL(data);
      link.remove();
    }, 100);
  }

  static showOrSaveEverything(dataBlob: Blob, fileTitle: string) {
    let newBlob = new Blob([dataBlob], { type: dataBlob.type });
    let fileName = `${fileTitle}`;
    // @ts-ignore
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      // @ts-ignore
      window.navigator.msSaveOrOpenBlob(newBlob, fileName);
      return;
    }

    const data = window.URL.createObjectURL(newBlob);

    let link = document.createElement('a');
    link.href = data;
    link.download = fileName;
    link.dispatchEvent(
      new MouseEvent('click', { bubbles: true, cancelable: true, view: window })
    );

    setTimeout(function () {
      window.URL.revokeObjectURL(data);
      link.remove();
    }, 100);
  }

  static showOrSavePdf(dataBlob: Blob, idxNr: string) {
    this.showOrSaveFile(
      dataBlob,
      `Dokument_${idxNr}`,
      FileType.Pdf
    );
  }

  static counter: number = 0;

  static showOrSaveCsv(dataBlob: Blob) {
    this.showOrSaveFile(dataBlob, `Report_${++this.counter}`, FileType.CSV);
  }

  static showOrSaveExcel(dataBlob: Blob) {
    this.showOrSaveFile(dataBlob, `Report_${++this.counter}`, FileType.EXCEL);
  }

  private static mimeType(arg: FileType) {
    switch (arg) {
      case FileType.Pdf:
        return 'application/pdf';
      case FileType.CSV:
        return 'text/csv';
      case FileType.Xml:
        return 'text/xml';
      case FileType.Json:
        return 'application/json';
      case FileType.EXCEL:
        return 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
    }
  }
}

export enum FileType {
  Pdf = 'pdf',
  Xml = 'xml',
  CSV = 'csv',
  Json = 'json',
  EXCEL = 'xlsx',
}
