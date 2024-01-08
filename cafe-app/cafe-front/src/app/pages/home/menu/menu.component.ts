import { Component, OnInit } from '@angular/core';
import { menuItems } from '../../../../assets/menu-data/menu-items-data';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{

  menuItems: any;

  ngOnInit(): void {
    this.menuItems = menuItems;
  }

}
