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
  message: String = '';

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
          console.log("data is : " + this.data);
        },
        error => {
          console.log(error);
          this.data = error.toString();
        },
        () => {
          if(this.data.toString() == "success"){
            this.message = "File Successfully Indexed!" ;
            this.popupTitle = "Success";
          }
          else if(this.data.toString() == "fail"){
            this.message = "File Indexing Failed!" ;
            this.popupTitle = "Fail";
          }
          else{
            this.message = this.data.toString() ;
            this.popupTitle = "Fail";
          }
          this.popUp();
        }
      );
    } else {
      console.log("please insert file");
      this.message = "Please Insert File" ;
      this.popupTitle = "Fail";
      this.popUp();
    }
  }

  popUp(){
    console.log("test");
    jQuery(this._model).trigger("open");
  }
}

