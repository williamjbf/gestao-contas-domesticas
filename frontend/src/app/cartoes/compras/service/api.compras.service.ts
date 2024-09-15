import { Injectable } from '@angular/core';
import axios from "axios";
import {environment} from "../../../../environments/environment";
import {Compra} from "../models/compra.model";

@Injectable({
  providedIn: 'root'
})
export class ComprasService {
  public apiURL = environment.apiURL;
  public baseURL = this.apiURL + "/cartoes/compras";

  constructor() { }

  public async cadastrar(compra: Compra): Promise<void> {
    try {
      await axios.post(this.baseURL, compra);
      console.log('Compra cadastrada com sucesso');
    } catch (error) {
      console.error('Erro ao cadastrar a Compra:', error);
    }
  }

  public async listar(idCartao: number | null) : Promise<Compra[]> {
    if (idCartao == null) {
      return this.listarTodas();
    } else {
      return this.listarPorCartao(idCartao);
    }
  }

  public async listarPorCartao(idCartao: number): Promise<Compra[]> {
    try {
      const response = await axios.get(
        this.apiURL + "/cartoes/" + idCartao + "/compras"
      );
      return response.data;
    } catch (error) {
      console.error('Erro ao listar compras do cartao' + idCartao + ' :', error);
      return [];
    }
  }

  public async listarTodas(): Promise<Compra[]> {
    try {
      const response = await axios.get(this.baseURL);
      return response.data;
    } catch (error) {
      console.error('Erro ao listar Todas as Compras:', error);
      return [];
    }
  }

  public async atualizar(idCompra: number, compra: Compra): Promise<void> {
    try {
      compra.id = idCompra;
      await axios.put(this.baseURL, compra);
      console.log('Compra atualizada com sucesso');
    } catch (error) {
      console.error('Erro ao atualizar a Compra:', error);
    }
  }

}
