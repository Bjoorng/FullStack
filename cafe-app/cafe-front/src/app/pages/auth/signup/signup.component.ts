import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ISignup } from '../../../interfaces/ISignup';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent {
  value: string | undefined;
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
  ]);
  username = new FormControl('', [Validators.required]);
  firstName = new FormControl('', [Validators.required]);
  lastName = new FormControl('', [Validators.required]);
  data: ISignup = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    username: '',
    phone: '',
  };
 hide = true;

  constructor(private authSvc: AuthService, private router: Router) {}

  signup() {
    this.authSvc.signUp(this.data).subscribe();
  }

  getErrorMessage() {
    if (this.email.hasError('required')) {
      console.log('email is required');

      return 'You must enter a value';
    }
    if (this.firstName.hasError('required')) {
      console.log('name is required');
      return 'You must enter a value';
    }
    if (this.firstName.hasError('required')) {
      console.log('name is required');
      return 'You must enter a value';
    }
    if (this.lastName.hasError('required')) {
      console.log('name is required');
      return 'You must enter a value';
    }
    if (this.username.hasError('required')) {
      console.log('username is required');
      return 'You must enter a value';
    }
    if (this.password.hasError('required')) {
      console.log('password is required');
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
}
