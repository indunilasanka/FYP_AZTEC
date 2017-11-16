import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { EmailValidator, EqualPasswordsValidator } from '../../theme/validators';

@Component({
  selector: 'change_passward',
  styleUrls: [('./changePassward.scss')],
  templateUrl: './changePassward.html',
})

export class ChangePassward implements OnInit {

  modalHeader: string;
  modalContent: string;
  public form: FormGroup;
  public submitted: boolean = false;
  public password: AbstractControl;
  public repeatPassword: AbstractControl;
  public passwords: FormGroup;

  constructor(private activeModal: NgbActiveModal, fb: FormBuilder) {
    this.form = fb.group({
      'passwords': fb.group({
        'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
        'repeatPassword': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
      }, { validator: EqualPasswordsValidator.validate('password', 'repeatPassword')})
    });
    this.passwords = <FormGroup> this.form.controls['passwords'];
    this.password = this.passwords.controls['password'];
    this.repeatPassword = this.passwords.controls['repeatPassword'];

  }


  ngOnInit() {}

  closeModal() {
    this.activeModal.close();
  }
}
