import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, Subject, catchError, map, of, tap, throwError } from 'rxjs';
import { IAuthData } from '../../interfaces/IAuthData';
import { ILogin } from '../../interfaces/ILogin';
import { ISignup } from '../../interfaces/ISignup';
import { environment } from '../../../environments/environment.development';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtHlper:JwtHelperService = new JwtHelperService()
  apiUrl:string = environment.url
  SignUpUrl:string = this.apiUrl + 'auth/signup';
  SignInUrl:string = this.apiUrl + 'auth/login';
  private authSubject = new BehaviorSubject<null | IAuthData>(null)
  user$ = this.authSubject.asObservable();
  isLoggedIn$ = this.user$.pipe(map(user => Boolean(user)));
  isAdmin$ = this.user$.pipe(map(user => Boolean(user?.username==='admin')));

  private userNotAuthenticatedSubject = new Subject<void>();
  userNotAuthenticated$ = this.userNotAuthenticatedSubject.asObservable();

  private userCredenzialiSubject = new Subject<void>();
  userCredenziali$ = this.userCredenzialiSubject.asObservable();


  constructor(private http:HttpClient, private router: Router) {}

  signUp(data:ISignup){

    return this.http.post<IAuthData>(this.SignUpUrl, data).pipe(
      catchError(error => {
        console.error(error);
        /*if (error.status) {
          console.error(error);
        }*/
        return throwError(error);
      })
    );
  }

  login(dati:ILogin){
    return this.http.post<IAuthData>(this.SignInUrl, dati)
    .pipe(tap(data =>{
      this.authSubject.next(data);
      localStorage.setItem('user', JSON.stringify(data));
      let user = localStorage.getItem('user');
      console.log(user);
      const expDate = this.jwtHlper.getTokenExpirationDate(data.accessToken) as Date;
      this.autoLogout(expDate);
      const userRoles = data.username;

       if (userRoles!=undefined && userRoles==='admin') {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/']);
      }

    }))
    .pipe(catchError(error => {
      console.error(error);
      if(error.error.message == "L'utente non è autenticato"){
        this.userNotAuthenticatedSubject.next();
        return of('');
        }
        else {
          this.userCredenzialiSubject.next();
        return throwError(error)}
          }))
    }


  logout(){
    this.authSubject.next(null);
    localStorage.removeItem('user')
    localStorage.removeItem('userLogged')
    this.router.navigate(['/login']);
  }
  autoLogTimer:any

  autoLogout(expDate:Date){
    const expMs:number = expDate.getTime() - new Date().getTime();

    this.autoLogTimer = setTimeout(() => {this.logout()}, expMs);
  }

  /*restoreUser(){
    const userJson = localStorage.getItem('user');
    const currentDate = Date.now()
    if(!userJson){
      return
    }
    const user:IAuthData = JSON.parse(userJson);
    if(this.jwtHlper.isTokenExpired(user.accessToken)){
      return
    }
    this.authSubject.next(user)
    console.log(' Already Logged In');
  }*/
}
