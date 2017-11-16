import { Component, Input } from '@angular/core';
import { DocumentModel } from '../../models/document.model';
import { RequestModel } from '../../models/request.model';
import { DocumentService } from './document.service';

@Component({
  selector: 'document',
  templateUrl: './document.html',
  styleUrls: ['./document.scss']
})

export class Document {

  data: Array<Object> = null;
  searchResults: Array<DocumentModel>;
  doc1: DocumentModel;
  doc2: DocumentModel;
  request: RequestModel = null;
  quary: string = '';
  reqId: string = '';
  isChecked: boolean = false;

  constructor(private documentServece: DocumentService) {
  }

  ngOnInit() {
  }

 searchDocuments(searchQuary: string) {
    console.log("searchDocuments()", searchQuary);
    this.documentServece.getDocuments(searchQuary,this.isChecked).subscribe(
      data => {
        this.data = data;
        this.loadDocuments();
      },
      error => console.log(error)
    );
  }

  loadDocuments() {
    this.searchResults = new Array<DocumentModel>();
    for (let entry of this.data) {
      console.log(entry);
      let doc:DocumentModel = new DocumentModel;
      doc.id = entry["id"];
      doc.title = entry["title"];
      doc.category = entry["category"];
      doc.summary = entry["summary"];
      doc.securityLevel = entry["securityLevel"];
      this.searchResults.push(doc);
    }
    console.log(this.data);
  }

  someMethod(event: string) {
   
    this.quary = event;
    console.log("query = ",event);
  }

  someMethod2(event: string) {
   
    this.reqId = event;
    console.log("req Id = ",event);
  }
}
