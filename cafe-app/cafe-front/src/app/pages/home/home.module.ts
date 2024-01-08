import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbCarousel, NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { CarouselComponent } from '../components/carousel/carousel.component';
import { MenuComponent } from './menu/menu.component';
import {MatCardModule} from '@angular/material/card';



@NgModule({
  declarations: [
    HomeComponent,
    CarouselComponent,
    MenuComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    NgbCarousel,
    NgbCarouselModule,
    MatCardModule
  ]
})
export class HomeModule { }
