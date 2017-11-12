import {Injectable} from '@angular/core';
import {BaThemeConfigProvider, colorHelper} from '../../../theme';

@Injectable()
export class ResultChartService {

  constructor(private _baConfig: BaThemeConfigProvider) {
  }

  private _results = [
    {
      fileName: '2017 tax.doc',
      fileCategory: 'leger',
      securityLvl: 'level1',
    },
    {
      fileName: '2016 tax.doc',
      fileCategory: 'leger',
      securityLvl: 'level1',
    },
  ];

  getAllResult() {
    return this._results;
  }

  getData() {
    let dashboardColors = this._baConfig.get().colors.dashboard;
    return [
      {
        value: 210,
        color: dashboardColors.white,
        highlight: colorHelper.shade(dashboardColors.white, 15),
        label: 'Level 1',
        percentage: 39,
        order: 1,
      }, {
        value: 54,
        color: dashboardColors.gossip,
        highlight: colorHelper.shade(dashboardColors.gossip, 15),
        label: 'Level 2 ',
        percentage: 15,
        order: 4,
      }, {
        value: 120,
        color: dashboardColors.silverTree,
        highlight: colorHelper.shade(dashboardColors.silverTree, 15),
        label: 'Level 3',
        percentage: 27,
        order: 3,
      }, {
        value: 60,
        color: dashboardColors.surfieGreen,
        highlight: colorHelper.shade(dashboardColors.surfieGreen, 15),
        label: 'Level 4',
        percentage: 8,
        order: 2,
      }, {
        value: 70,
        color: dashboardColors.blueStone,
        highlight: colorHelper.shade(dashboardColors.blueStone, 15),
        label: 'Level 5',
        percentage: 10,
        order: 0,
      },
    ];
  }
}
