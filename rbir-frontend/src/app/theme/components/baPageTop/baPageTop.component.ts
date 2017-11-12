import {Component} from '@angular/core';

import {GlobalState} from '../../../global.state';

import { UserService } from '../../../services/user.service';

@Component({
  selector: 'ba-page-top',
  templateUrl: './baPageTop.html',
  styleUrls: ['./baPageTop.scss']
})
export class BaPageTop {


  data: Array<Object> = null;

  ngOnInit() {
      this.userService.getUser().subscribe(
      data => {
        console.log(data);
        this.data = data;
      },
      error => console.log(error)
    );
  }

  public isScrolled:boolean = false;
  public isMenuCollapsed:boolean = false;

  constructor(private _state:GlobalState,private userService: UserService) {
    this._state.subscribe('menu.isCollapsed', (isCollapsed) => {
      this.isMenuCollapsed = isCollapsed;
    });
  }

  public toggleMenu() {
    this.isMenuCollapsed = !this.isMenuCollapsed;
    this._state.notifyDataChanged('menu.isCollapsed', this.isMenuCollapsed);
    return false;
  }

  public scrolledChanged(isScrolled) {
    this.isScrolled = isScrolled;
  }
}
