import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CapitalizeFirstLetterPipe } from './capitalize-first-letter.pipe';
import { KeysPipe } from './keys.pipe';
import { GenerateSelectBoxOptionsPipe } from './generate-select-box-options.pipe';



@NgModule({
  declarations: [
    CapitalizeFirstLetterPipe,
    KeysPipe,
    GenerateSelectBoxOptionsPipe,

  ],
  imports: [
    CommonModule
  ],
  exports: [
    CapitalizeFirstLetterPipe,
    KeysPipe,
    GenerateSelectBoxOptionsPipe,
  ]
})
export class SharedPipesModule { }
