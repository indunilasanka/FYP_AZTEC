import {Component} from '@angular/core';

import {RequestService} from './request.service';

@Component({
  selector: 'request',
  templateUrl: './request.html',
  styleUrls: ['./request.scss']
})
export class Request {

  public feed:Array<Object>;

  constructor(private _requestService:RequestService) {
  }

  ngOnInit() {
    this._loadFeed();
  }

  expandMessage (message){
    message.expanded = !message.expanded;
  }

  private _loadFeed() {
    this.feed = this._requestService.getData();
  }
}
