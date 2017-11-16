/**
 * Created by EMS on 6/1/2017.
 */

import { Component } from '@angular/core';
import { FormGroup, AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { EmailValidator, EqualPasswordsValidator } from '../../theme/validators';

@Component({
  selector: 'create_user',
  templateUrl: './createUser.html',
  styleUrls: ['./createUser.scss'],
})
export class CreateUser {

  public form: FormGroup;
  public name: AbstractControl;
  public lname: AbstractControl;
  public email: AbstractControl;
  public password: AbstractControl;
  public repeatPassword: AbstractControl;
  public passwords: FormGroup;
  public level: AbstractControl;

  public submitted: boolean = false;

  constructor(fb:FormBuilder) {
    
        this.form = fb.group({
          'name': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
          'lname': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
          'email': ['', Validators.compose([Validators.required, EmailValidator.validate])],
          'level': ['', Validators.compose([Validators.required])],
          'passwords': fb.group({
            'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
            'repeatPassword': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
          }, { validator: EqualPasswordsValidator.validate('password', 'repeatPassword')})
        });
    
        this.name = this.form.controls['name'];
        this.email = this.form.controls['email'];
        this.passwords = <FormGroup> this.form.controls['passwords'];
        this.password = this.passwords.controls['password'];
        this.repeatPassword = this.passwords.controls['repeatPassword'];
        this.lname = this.form.controls['lname'];
        this.level = this.form.controls['level'];
  }


  public onSubmit(values:Object):void {
    this.submitted = true;
    if (this.form.valid) {
      // your code goes here
      // console.log(values);
    }
  }
}
