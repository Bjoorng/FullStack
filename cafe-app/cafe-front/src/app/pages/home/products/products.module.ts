import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductsComponent } from './products.component';
import { CoffeeComponent } from './coffee/coffee.component';
import { TeaComponent } from './tea/tea.component';


@NgModule({
  declarations: [
    ProductsComponent,
    CoffeeComponent,
    TeaComponent,
  ],
  imports: [
    CommonModule,
    ProductsRoutingModule
  ]
})
export class ProductsModule { }
