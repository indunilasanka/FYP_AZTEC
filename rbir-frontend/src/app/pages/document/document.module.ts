/**
 * Created by EMS on 6/1/2017.
 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgaModule } from '../../theme/nga.module';
import { FormsModule as AngularFormsModule } from '@angular/forms';

import { routing } from './document.routing';
import { Document } from './document.component';
import { DocumentService } from './document.service';
import {SearchResult} from './searchResult/searchResult.component';
import {Request} from './request/request.component';
import {RequestService} from './request/request.service';


@NgModule({
  imports: [
    CommonModule,
    NgaModule,
    routing,
    AngularFormsModule,
  ],
  declarations: [
    Document,
    SearchResult,
    Request,
  ],
  providers: [
    DocumentService,
    RequestService,
  ],

})
export class DocumentModule {
}
