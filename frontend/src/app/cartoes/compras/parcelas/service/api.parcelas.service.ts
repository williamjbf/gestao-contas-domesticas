import { Injectable } from '@angular/core';
import axios from '../../../../interceptors/axios.interceptor';
import {environment} from "../../../../../environments/environment";
import {StatusPagamento} from "../models/statuspagamento.model";

@Injectable({
  providedIn: 'root'
})
export class ParcelaService {
  public apiURL = environment.apiURL;
  public baseURL = this.apiURL + "/cartoes/compras/parcelas";

  constructor() { }

  public async alterarStatusPagamento(idParcela: number | null, status: StatusPagamento) : Promise<void> {
    try {
      await axios.put(this.baseURL + "/" + idParcela, status);
      console.log('Parcela atualizada sucesso');
    } catch (error) {
      console.error('Erro ao atualizar a Parcela:', error);
    }
  }

}
