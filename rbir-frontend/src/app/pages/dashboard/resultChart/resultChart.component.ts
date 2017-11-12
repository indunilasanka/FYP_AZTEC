import {Component} from '@angular/core';

import { ResultChartService } from './resultChart.service';
import { CateResult } from '../../../models/categorization_result.model';

import * as Chart from 'chart.js';

@Component({
  selector: 'result-chart',
  templateUrl: './resultChart.html',
  styleUrls: ['./resultChart.scss']
})

// TODO: move chart.js to it's own component
export class ResultChart {

  public doughnutData: Array<Object>;
  results: Array<CateResult>;

  

  constructor(private resultChartService: ResultChartService) {
    this.doughnutData = resultChartService.getData();
    this.results = resultChartService.getAllResult();
  }

  ngAfterViewInit() {
    this._loadDoughnutCharts();
  }

  private _loadDoughnutCharts() {
    let el = jQuery('.chart-area').get(0) as HTMLCanvasElement;
    new Chart(el.getContext('2d')).Doughnut(this.doughnutData, {
      segmentShowStroke: false,
      percentageInnerCutout : 64,
      responsive: true
    });
  }
}
