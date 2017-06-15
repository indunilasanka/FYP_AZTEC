import {Component, Input} from '@angular/core';
import {layoutPaths} from '../../../theme';

@Component({
  selector: 'search-result',
  templateUrl: './searchResult.html',
  styleUrls: ['./searchResult.scss']
})
export class SearchResult {

  path:String = layoutPaths.images.profile+"doc.jpg";
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
