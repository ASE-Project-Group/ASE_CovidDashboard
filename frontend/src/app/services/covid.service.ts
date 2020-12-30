import {Inject, Injectable} from '@angular/core';
import {CovidCasesDaily} from '../model/covid-cases-daily';
import {HttpClient} from '@angular/common/http';

import {HospitalBedsDaily} from "../model/hospital-beds-daily";

import { CovidDeathsDaily} from "../model/covid-deaths-daily";
import {CovidHospitalizationsDaily} from "../model/covid-hospitalizations-daily";
import {Provinces} from "../model/Provinces";
import {TableDataDaily} from "../model/tableDataDaily";


@Injectable({
  providedIn: 'root',
})
export class CovidService {

  constructor(@Inject('BACKEND_API_URL') private apiUrl: string, private http: HttpClient) {
  }

  public getNewCasesPerDate(): Promise<CovidCasesDaily[]> {
    return this.http.get(this.apiUrl + '/daily/10')
      .toPromise().then(item => (item as {cases: CovidCasesDaily[]}).cases);
  }


  public getHospitalBedsPerDate(): Promise<HospitalBedsDaily[]> {
    return this.http.get(this.apiUrl + '/daily/hospital/10')
      .toPromise().then(item => (item as {situations: HospitalBedsDaily[]}).situations);
  }

  public getTableDataPerDate(): Promise<TableDataDaily[]> {
    return this.http.get(this.apiUrl + '/daily/tableData/100')
      .toPromise().then(item => (item as {data: TableDataDaily[]}).data);
  }

  getProvinces() {
    return this.http.get<any>(this.apiUrl + '/provinces')
      .toPromise()
      .then(res => <Provinces[]>res.data)
      .then(data => { return data; });
  }

}
