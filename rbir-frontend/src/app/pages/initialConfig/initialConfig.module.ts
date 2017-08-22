import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgaModule } from '../../theme/nga.module';

import { InitialConfig } from './initialConfig.component';
import { routing } from './initialConfig.routing';


import { FeedService } from './feed/feed.service';
import { FileUploader } from './fileUploader';
import { FileUploadService } from './fileUploader/fileUpload.service';
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppTranslationModule,
    NgaModule,
    routing,
    Ng2Bs3ModalModule,
  ],
  declarations: [
    InitialConfig,
    FileUploader,
  ],
  providers: [
    FeedService,
    FileUploadService,
  ],
})

export class InitialConfigModule {}
