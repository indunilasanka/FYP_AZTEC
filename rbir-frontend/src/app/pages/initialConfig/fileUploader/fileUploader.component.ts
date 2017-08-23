import { Component, ViewChild, Input, Output, EventEmitter, ElementRef, Renderer } from '@angular/core';

import { FileUploadService } from './fileUpload.service';
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import { DocumentModel } from '../../../models/document.model';

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
  @Input() selectedSLvl: string = null;

  @ViewChild('fileUpload') public _fileUpload: ElementRef;
  @ViewChild('inputText') public _inputText: ElementRef;
  @ViewChild('modal') public _model: ElementRef;

  data: Object = null;
  fileName: string = '';
  popupTitle: String = '';
  popupMessage: String = '';
  documents: DocumentModel[] = [];
  fileList: File[] = null;

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
    const files = this._fileUpload.nativeElement.files;
    console.log("selectted level -------------------->", this.selectedSLvl);
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
            const document: DocumentModel = new DocumentModel();
            document.securityLevel = this.selectedSLvl;
            document.file = files.item(i);
            console.log(i, files.item(i).name, document.securityLevel);
            this.documents.push(document);
          }
          // console.log(fielType);
        }
      }
    }
  }


  removeDocument(document: DocumentModel) {
    this.documents = this.documents.filter(item => item !== document);
  }

  startFileupload() {

    if (this.documents) {
      this.fileUploadService.uploadFolder(this.documents).subscribe(


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

