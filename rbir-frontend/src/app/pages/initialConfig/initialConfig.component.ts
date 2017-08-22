import { Component } from '@angular/core';
import { NgUploaderOptions } from 'ngx-uploader';

@Component({
  selector: 'initial-config',
  styleUrls: ['./initialConfig.scss'],
  templateUrl: './initialConfig.html',
})
export class InitialConfig {

  numberOfLevel: number;
  securityLvl: String[];
  selectLvl: String;

  public fileUploaderOptions: NgUploaderOptions = {
    // url: 'http://website.com/upload'
    url: '',
  };

  constructor() {
  }

  setSecurityLevel(numberOfLevels: number) {
    this.numberOfLevel = numberOfLevels;
    this.securityLvl = [];
    for (let i = 1; i <= this.numberOfLevel; i++) {
      this.securityLvl.push('Security Level ' + i);
    }
  }

  selectSecurityLvl(lvl: String) {
    this.selectLvl = lvl;
  }
}
