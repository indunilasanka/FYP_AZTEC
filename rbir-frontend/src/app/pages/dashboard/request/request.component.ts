import {Component} from '@angular/core';

import {RequestService} from './request.service';

@Component({
  selector: 'request',
  templateUrl: './request.html',
  styleUrls: ['./request.scss']
})
export class Request {

  public feed:Array<Object>;

  constructor(private _requestService: RequestService) {
  }

  ngOnInit() {
  }
}
