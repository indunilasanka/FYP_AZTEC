import {Injectable} from '@angular/core';

import { Authentication } from '../../../models/request.model';

@Injectable()
export class RequestService {

  private _requests = [
    {
      user: {
        name : 'Saranga',
        occupation: 'Student',
        address: 'No:2, Rathmahara, Gonapinuwala',
        telephone: '0714578283',
        email: 'saeanga@gmail.com',
        profPic: '',
      },
      authenticator: {
        name : 'perera',
        occupation: 'Accountant',
        address: 'Bandaranayaka Mawata, Katubedda, Moratuwa',
        telephone: '0114008283',
        email: 'perera@gmail.com',
        profPic: '',
      },
      header: 'Tax details',
      content: '2017 tax report asjdlks kajsdj asjdlkjklasd adsadsad zxccasdsd',
      attachment: null,
      authentication: Authentication.NOTNEEDED,
      finished: false,
    },
  ];

  getRequest() {
    return this._requests;
  }
}
