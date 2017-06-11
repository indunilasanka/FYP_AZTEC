import {Component} from '@angular/core';
import {DocumentModel} from '../../models/document.model'

import {DocumentService} from './document.service';

@Component({
  selector: 'document',
  templateUrl: './document.html',
  styleUrls: ['./document.scss']
})

export class Document {

  data: Object = null;
  searchResults : Array<DocumentModel>;
  doc1 : DocumentModel;
  doc2 : DocumentModel;

  constructor(private documentServece: DocumentService) {
  }

  ngOnInit() {

  }

  searchDocuments(searchQuary : string){
    console.log("searchDocuments()", searchQuary);
    this.loadDocuments();
  }

  loadDocuments(){

    this.searchResults = new Array<DocumentModel>();
    this.doc1 = new DocumentModel;
    this.doc1.title = "File1.txt";
    this.doc1.summary = "Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);


    this.documentServece.getDocuments().subscribe(
      data => this.data = data,
      error => console.log(error)
    );

    console.log(this.data);


  }
}
