import {Component, ViewChild, Input, Output, EventEmitter, ElementRef, Renderer} from '@angular/core';

import {FileUploadService} from './fileUpload.service';
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';

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
  @ViewChild('modal') public _model: ElementRef;

  data: Object = null;
  fileName: string = '';
  file: File = null;
  popupTitle: String = '';
  popupMessage: String = '';

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
      this.file = files[0];
      this.fileName = this.file.name;
      console.log("file name is   ", this.fileName);
    }
  }

  startFileupload() {
    if (this.file) {
      this.fileUploadService.uploadFiles(this.file).subscribe(
        data => {
          this.data = data;
          console.log('data is : ' + this.data);
          if (this.data.toString() === 'success' ) {
            this.popUp('Success', 'File Successfully Indexed!');
          }
          else if(this.data.toString() === "Invalid File Type")
            this.popUp( 'Fail', 'Invalid File Type!');
          else {
            this.popUp( 'Fail', 'File Indexing Failed!');
          }
        },
        error => {
          console.log('error ' + error.toString());
          this.data = error.toString();
          this.popUp('Fail', this.data.toString());
        },
      );
    } else {
      console.log("please insert file");
      this.popUp('Fail', 'Please Insert File');
    }
  }

  popUp(title: String, message: String) {
    console.log("test");
    this.popupTitle = title;
    this.popupMessage = message;
    jQuery(this._model).trigger("open");
  }
}

