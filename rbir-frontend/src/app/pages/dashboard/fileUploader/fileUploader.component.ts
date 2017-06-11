import {Component, ViewChild, Input, Output, EventEmitter, ElementRef, Renderer} from '@angular/core';
import {NgUploaderOptions} from 'ngx-uploader';

import {FileUploadService} from './fileUpload.service';

@Component({
  selector: 'file-uploader-com',
  styleUrls: ['./fileUploader.scss'],
  templateUrl: './fileUploader.html',
})
export class FileUploader {


  // @Input() fileUploaderOptions: NgUploaderOptions = { url: '' };
  // @Output() onFileUpload = new EventEmitter<any>();
  @Output() onFileUploadCompleted = new EventEmitter<any>();
  @Input() defaultValue: string = '';

  @ViewChild('fileUpload') public _fileUpload: ElementRef;
  @ViewChild('inputText') public _inputText: ElementRef;

  data: Object = null;


  public uploadFileInProgress: boolean;

  constructor(private renderer: Renderer, private fileUploadService: FileUploadService) {
    // constructor(private renderer: Renderer) {
  }

  bringFileSelector(): boolean {
    console.log("bringFileSelector()");
    this.renderer.invokeElementMethod(this._fileUpload.nativeElement, 'click');
    return false;
  }

  beforeFileUpload($event) {
    console.log("fileChange($event)");
    let files = this._fileUpload.nativeElement.files;
    if (files.length) {
      const file = files[0];
      console.log(file);
      this.fileUploadService.uploadFiles(file).subscribe(
        data => this.data = data,
        error => console.log(error)
      );

    }
  }
}

//
// beforeFileUpload(event): void {
//   let files = this._fileUpload.nativeElement.files;
//   if (files.length) {
//     const file = files[0];
//     this._onChangeFileSelect(files[0])
//     this.dashboardService.uploadFiles(file).subscribe(
//       data => this.data = data,
//       error => console.log(error)
//     );
//   }
// }
//
// _onFileUpload(data): void {
//   if (data['done'] || data['abort'] || data['error']) {
//     this._onFileUploadCompleted(data);
//   } else {
//     this.onFileUpload.emit(data);
//   }
// }
