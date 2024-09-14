import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import axios from "axios";

interface Cartao {
  id: number,
  limite: number;
  descricao: string;
}

@Injectable({
  providedIn: 'root'
})
export class CartaoService {
  public apiURL = environment.apiURL;
  public baseURL = this.apiURL + "/cartoes";

  constructor() { }

  public async cadastrar(cartao: Cartao): Promise<void> {
    try {
      await axios.post(this.baseURL, cartao);
      console.log('Cartão cadastrado com sucesso');
    } catch (error) {
      console.error('Erro ao cadastrar cartão:', error);
    }
  }

  public async listar(): Promise<Cartao[]> {
    try {
      const response = await axios.get(this.baseURL);
      return response.data;
    } catch (error) {
      console.error('Erro ao listar cartões:', error);
      return [];
    }
  }

  public async atualizar(idCartao: number, cartao: Cartao): Promise<void> {
    try {
      cartao.id = idCartao;
      await axios.put(this.baseURL, cartao);
      console.log('Cartão atualizado com sucesso');
    } catch (error) {
      console.error('Erro ao atualizado cartão:', error);
    }
  }

}
