import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HospitalUtilizationComponent } from './hospital-utilization.component';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {ChartModule} from 'primeng/chart';
import {RadioButtonModule} from 'primeng/radiobutton';
import {FormsModule} from '@angular/forms';
import {TreedropdownModule} from '../treedropdown/treedropdown.module';



@NgModule({
  declarations: [HospitalUtilizationComponent],
  exports: [
    HospitalUtilizationComponent
  ],
    imports: [
        CommonModule,
        ProgressSpinnerModule,
        ChartModule,
        RadioButtonModule,
        FormsModule,
        TreedropdownModule,
    ],
})
export class HospitalUtilizationModule { }
