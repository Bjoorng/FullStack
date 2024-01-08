import { Component } from '@angular/core';
import { ILogin } from '../../../interfaces/ILogin';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  hide = true;
  value: string | undefined;
  isValid:boolean = false;
  credentials:boolean = false;
  errorMessage: string | null = null;


  data:ILogin = {
    username: '',
    password: ''
  }


  constructor(private authSvc:AuthService,private router: Router){}

  login() {
    this.authSvc.login(this.data).subscribe(
      (data) => {
        console.log('User Logged In', data);
        this.router.navigateByUrl('/');
      },
      (error) => {
        console.error('Login Error', error);
        if (error.status === 401) {
          this.displayErrorMessage('Invalid username or password.');
        } else {
          this.displayErrorMessage('An error occurred during login.');
        }
      }
    );
  }

  private displayErrorMessage(message: string): void {
    this.errorMessage = message;
  }

}
