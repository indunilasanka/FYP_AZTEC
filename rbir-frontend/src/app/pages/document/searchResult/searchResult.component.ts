import {Component, Input} from '@angular/core';

@Component({
  selector: 'search-result',
  templateUrl: './searchResult.html',
  styleUrls: ['./searchResult.scss']
})
export class SearchResult {

  @Input() searchResults:Array<Object>;

  constructor() {
  }

  ngOnInit() {
  }

  expandMessage (message){
    message.expanded = !message.expanded;
  }

  test(){
    console.log("test clicked" ,this.searchResults);
  }
}
