import { Component, ViewChild, Input, Output, EventEmitter, ElementRef, Renderer } from '@angular/core';

import { FileUploadService } from './fileUpload.service';
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
  popupTitle: String = '';
  popupMessage: String = '';
  fileList: File[] = null;

  public uploadFileInProgress: boolean;

  constructor(private renderer: Renderer, private fileUploadService: FileUploadService) {
    // constructor(private renderer: Renderer) {
  }

  bringFileSelector(): boolean {
    this.renderer.invokeElementMethod(this._fileUpload.nativeElement, 'click');
    return false;
  }

  beforeFileUpload($event) {
    const files = this._fileUpload.nativeElement.files;
    this.fileList = [];
    if (files.length) {
      const fileCount = files.length;
      if (fileCount > 0) {

        const firstFile: File = files[0];
        this.fileName = firstFile.webkitRelativePath;
        const fileNamepart: string[] = this.fileName.split('/');
        this.fileName = fileNamepart[0];

        for (let i = 0; i < fileCount; i++) {
          const fielType: String = files.item(i).type;
          if (fielType.includes('pdf') || fielType.includes('officedocument.word')) {
            this.fileList.push(files.item(i));
          }

          // console.log(fielType);
        }
      }
    }
  }


  removeDocument(document: File) {
    this.fileList = this.fileList.filter(item => item !== document);
  }

  startFileupload() {

    if (this.fileList) {
      this.fileUploadService.uploadFolder(this.fileList).subscribe(


        data => {
          this.data = data;
          if (this.data.toString() === 'success') {
            this.popUp('Success', 'File Successfully Indexed!');
          } else {
            this.popUp('Fail', 'File Indexing Failed!');
          }
        },
        error => {
          this.data = error.toString();
          this.popUp('Fail', this.data.toString());
        },
      );

    }
  }

  popUp(title: String, message: String) {
    console.log("test");
    this.popupTitle = title;
    this.popupMessage = message;
    jQuery(this._model).trigger("open");
  }
}

