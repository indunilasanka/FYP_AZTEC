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
  @Output() requestSender = new EventEmitter<String>();

  data: Array<Object> = null;

  constructor(private _requestService: RequestService) {
  }

  ngOnInit() {
    this.loadFeed();
  }

  expandMessage(message) {
    message.expanded = !message.expanded;
  }

  private loadFeed() {
    this._requestService.getRequest().subscribe(
      data => {
        console.log(data);
        this.data = data;
      },
      error => console.log(error)
    );
  }

  startSearch(request: String) {
    this.requestSender.emit(request);
  }

  closeSearch(i:number,email:string, requestId:string,request:string) {

    this.data.splice(i,1); 

    this._requestService.deleteRequest(email,requestId,request).subscribe(
      data => {
        console.log(data);
        this.data = data;
      },
      error => console.log(error)
    );


    console.log(i,email,requestId,request);
  }
}
