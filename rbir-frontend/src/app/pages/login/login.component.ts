import { Component } from '@angular/core';
import { FormGroup, AbstractControl, FormBuilder, Validators, FormControl} from '@angular/forms';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../services/authentication.service';


@Component({
  selector: 'login',
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login {

  public form:FormGroup;
  public email:AbstractControl;
  public password:AbstractControl;
  public submitted:boolean = false;
  public error: string;
  private _selector:string = 'preloader';
  private _element:HTMLElement;
  public loginInProgress:boolean;


  constructor(fb:FormBuilder,private router: Router,
    private authenticationService: AuthenticationService) {
    this.form = fb.group({
      'email': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
    });

    this.email = this.form.controls['email'];
    this.password = this.form.controls['password'];
  }

  ngOnInit() {
        // reset login status
        this.authenticationService.logout();
        this._element = document.getElementById(this._selector);
        this.loginInProgress=false;
  }

  public show():void {
    this._element.style['display'] = 'block';
  }

  public hide(delay:number = 0):void {
    setTimeout(() => {
      this._element.style['display'] = 'none';
    }, delay);
  }


  public onSubmit(values:Object):void {
    this.submitted = true;
    if (this.form.valid) {
      console.log("submitting password for service");
      this.loginInProgress=true;
      this.authenticationService.login(this.email.value,this.password.value).subscribe(
      result => {
         if (result === true) {
                    // login successful
              console.log("navigating to dashboard")
              this.loginInProgress=false;
              this.router.navigate(['/pages/dashboard']);

        } 
        
      },
      error => console.log(error)
    );
    }
  }
}
