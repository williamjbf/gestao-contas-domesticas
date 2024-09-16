import { Injectable } from '@angular/core';
import { Observable, Subscriber } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';
import { EventSourcePolyfill } from 'event-source-polyfill/src/eventsource.min.js';
import { environment } from '../../../environments/environment';
import {Notificacao} from "../models/notificacao.model";

@Injectable({
  providedIn: 'root'
})
export class NotificacaoService {
  private apiUrl = `${environment.apiURL}/contas/pagar/proximas/vencimento`;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  getNotificacoes(): Observable<Notificacao> {
    return new Observable((observer: Subscriber<Notificacao>) => {
      if (isPlatformBrowser(this.platformId)) {
        const eventSource = new EventSourcePolyfill(this.apiUrl, {
          headers: {
            'Authorization': `${localStorage.getItem('Auth_JWT_Bearer_Token')}`
          }
        });

        eventSource.onmessage = (event: MessageEvent) => {
          console.log(event.data)
          observer.next(JSON.parse(event.data));
        };

        eventSource.onerror = (error: any) => {
          console.error('EventSource error:', error);
          console.error('Error details:', {
            type: error.type,
            status: error.status,
            statusText: error.statusText,
            headers: error.headers
          });
          observer.error(error);
          eventSource.close();
        };

        eventSource.onopen = () => {
          console.log('Connection to server opened.');
        };

        return () => {
          eventSource.close();
        };
      } else {
        return () => {};
      }
    });
  }
}
