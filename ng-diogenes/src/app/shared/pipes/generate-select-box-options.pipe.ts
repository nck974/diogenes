import { Pipe, PipeTransform } from '@angular/core';
import { Category } from '../../models/Category';

@Pipe({
  name: 'generateSelectBoxOptions'
})
export class GenerateSelectBoxOptionsPipe implements PipeTransform {

  transform(values: Category[], ...args: unknown[]): [string, string | number][] {
    console.log(`Mapping categories ${values.length}`)
    return values.map(category => [category.name, category.id]);
  }

}
