import { Pipe, PipeTransform } from '@angular/core';
import { Category } from '../../models/Category';
import { Location } from 'src/app/models/Location';

@Pipe({
    name: 'generateSelectBoxOptions',
    standalone: false
})
export class GenerateSelectBoxOptionsPipe implements PipeTransform {

  transform(values: Category[] | Location[], ...args: unknown[]): [string, string | number][] {
    return values.map(value => [value.name, value.id]);
  }

}
