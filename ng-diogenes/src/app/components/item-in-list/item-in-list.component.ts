import { Component, Input } from '@angular/core';
import { Item } from 'src/app/models/Item';

@Component({
  selector: 'app-item-in-list',
  templateUrl: './item-in-list.component.html',
  styleUrls: ['./item-in-list.component.scss']
})
export class ItemInListComponent {

  @Input() item!: Item;

  onDelete(){
    console.log("Deleting item " + this.item.id);
  }
  
  onOpenDetails(){
    console.log("Opening item " + this.item.id);
  }

  getAvatarColor(): string{
    if (this.item.category != null){
      let color = this.item.category.color;
      const hexColorRegex = /^(?:[0-9a-fA-F]{3}){1,2}$/;
      if (hexColorRegex.test(color)){
        return `#${color}`;
      }
    }
    return "#ddd";
  }
}
