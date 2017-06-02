import {Component} from '@angular/core';
import {DocumentModel} from '../../models/document.model'

import {DocumentService} from './document.service';

@Component({
  selector: 'document',
  templateUrl: './document.html',
  styleUrls: ['./document.scss']
})

export class Document {

  searchResults : Array<DocumentModel>;
  doc1 : DocumentModel;
  doc2 : DocumentModel;

  constructor() {
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

    this.doc1 = new DocumentModel;
    this.doc1.title = "File2.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File3.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File4.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File5.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File6.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File7.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);this.doc1 = new DocumentModel;


    this.searchResults = new Array<DocumentModel>();
    this.doc1 = new DocumentModel;
    this.doc1.title = "File1.txt";
    this.doc1.summary = "Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File2.txt";
    this.doc1.summary = "Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatusI wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File3.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File4.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File5.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File6.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);

    this.doc1 = new DocumentModel;
    this.doc1.title = "File7.txt";
    this.doc1.summary = "I wanted to find something to put in between the In the same way that I might put string or number there to say what type the variable has. Something like Brocco just did but with just one line if that is possible.Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus Sorry maybe my question was not clear. What I wanted to do was to find a definition for userTestStatus so that Typescript would allow me to check where it is used and so for example I could not enter userTestStatus";
    this.searchResults.push(this.doc1);this.doc1 = new DocumentModel;

  }
}
