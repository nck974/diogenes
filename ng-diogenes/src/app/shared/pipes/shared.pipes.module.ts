import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CapitalizeFirstLetterPipe } from './capitalize-first-letter.pipe';
import { KeysPipe } from './keys.pipe';
import { GenerateSelectBoxOptionsPipe } from './generate-select-box-options.pipe';
import { SecureImagePipe } from './secure-image.pipe';



@NgModule({
  declarations: [
    CapitalizeFirstLetterPipe,
    KeysPipe,
    GenerateSelectBoxOptionsPipe,
    SecureImagePipe,

  ],
  imports: [
    CommonModule
  ],
  exports: [
    CapitalizeFirstLetterPipe,
    KeysPipe,
    GenerateSelectBoxOptionsPipe,
    SecureImagePipe
  ]
})
export class SharedPipesModule { }
