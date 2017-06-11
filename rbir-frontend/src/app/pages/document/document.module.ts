/**
 * Created by EMS on 6/1/2017.
 */
import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { NgaModule } from '../../theme/nga.module';

import { routing }       from './document.routing';
import { Document } from './document.component';
import { DocumentService } from './document.service'
import {SearchResult}  from './searchResult/searchResult.component'

@NgModule({
  imports: [
    CommonModule,
    NgaModule,
    routing
  ],
  declarations: [
    Document,
    SearchResult
  ],
  providers: [
    DocumentService
  ]

})
export class DocumentModule {
}
