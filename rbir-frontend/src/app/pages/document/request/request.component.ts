import {Component, Output, EventEmitter} from '@angular/core';

import {RequestService} from './request.service';
import {RequestModel} from '../../../models/request.model';
import {UserModel} from '../../../models/user.model';


@Component({
  selector: 'request',
  templateUrl: './request.html',
  styleUrls: ['./request.scss']
})
export class Request {

  public requests: Array<RequestModel>;
  @Output() requestSender = new EventEmitter<RequestModel>();

  constructor(private _requestService: RequestService) {
  }

  ngOnInit() {
    this.loadFeed();
  }

  expandMessage(message) {
    message.expanded = !message.expanded;
  }

  private loadFeed() {
    this.requests = this._requestService.getRequest();
  }

  startSearch(request: RequestModel) {
    this.requestSender.emit(request);
  }

  closeSearch() {
    console.log("closeSearch()");
  }
}
