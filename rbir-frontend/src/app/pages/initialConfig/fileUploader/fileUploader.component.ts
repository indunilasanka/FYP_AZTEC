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

  @ViewChild('fileUpload') public _fileUpload: ElementRef;
  @ViewChild('inputText') public _inputText: ElementRef;
  @ViewChild('modal') public _model: ElementRef;

  numberOfLevel: number;
  securityLvls: string[];
  selectedLvl: string;
  data: Object = null;
  fileName: string = '';
  popupTitle: String = '';
  popupMessage: String = '';
  documents: DocumentModel[] = [];
  fileList: File[] = null;

  uploadFileInProgress: boolean;

  constructor(private renderer: Renderer, private fileUploadService: FileUploadService) { }

  setSecurityLevel(numberOfLevels: number) {
    this.numberOfLevel = numberOfLevels;
    this.securityLvls = [];
    for (let i = 1; i <= this.numberOfLevel; i++) {
      this.securityLvls.push('security_level_' + i);
    } this.selectedLvl = 'security_level_1';
  }

  setSelectedLvl(lvl: string) {
    this.selectedLvl = lvl;
  }

  bringFileSelector(): boolean {
    console.log("bringFileSelector()");
    this.renderer.invokeElementMethod(this._fileUpload.nativeElement, 'click');
    return false;
  }

  beforeFileUpload($event) {
    const files = this._fileUpload.nativeElement.files;
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
            document.securityLevel = this.selectedLvl;

            console.log('document.securityLevel -----' , document.securityLevel);
            document.file = files.item(i);
            this.documents.push(document);
          }
        }
      }
    }
  }


  removeDocument(document: DocumentModel) {
    this.documents = this.documents.filter(item => item !== document);
  }

  startFileupload() {

    if (this.documents) {
      this.fileUploadService.uploadFolder(this.documents, this.securityLvls).subscribe(


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

