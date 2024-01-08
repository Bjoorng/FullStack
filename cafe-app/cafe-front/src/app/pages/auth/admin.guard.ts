import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { map } from 'rxjs';

export const adminGuard: CanActivateFn = (route, state) => {
  const AuthSvc = inject(AuthService);
  const router = inject(Router)
  return AuthSvc.isAdmin$.pipe(map(isAdmin$ => {
    if (!isAdmin$){
      router.navigate(['/home'])
    }
      return isAdmin$
  }))
}
