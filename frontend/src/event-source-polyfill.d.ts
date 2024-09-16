// Necessário para usar o event-source-polyfill em notificacao.service.ts
declare module 'event-source-polyfill/src/eventsource.min.js' {
  export const EventSourcePolyfill: any;
}
