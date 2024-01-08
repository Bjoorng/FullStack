import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './products.component';
import { CoffeeComponent } from './coffee/coffee.component';
import { TeaComponent } from './tea/tea.component';

const routes: Routes = [
  { path: '', component: ProductsComponent },
  { path: 'coffee', component: CoffeeComponent },
  { path: 'tea', component: TeaComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
