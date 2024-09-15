import {Injectable} from "@angular/core";
import {environment} from "../../../../environments/environment";
import {ContaPagar} from "../models/conta.pagar.model";
import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class ContasPagarService {
  public apiUrl = environment.apiURL;
  public baseUrl = this.apiUrl + "/contas/pagar";

  constructor() {
  }

  public async cadastrar(contaPagar: ContaPagar): Promise<void> {
    try {
      await axios.post(this.baseUrl, contaPagar);
      console.log('Conta a pagar cadastrada com sucesso')
    } catch (error) {
      console.error('Erro ao cadastrar a conta a pagar:', error);
    }
  }

  public async listar(): Promise<ContaPagar[]>{
    return this.listarTodas();
  }

  public async listarTodas(): Promise<ContaPagar[]>{
    try {
      const response = await axios.get(this.baseUrl);
      return response.data;
    } catch (error) {
      console.error('Erro ao listar Todas as Contas a Pagar:', error);
      return [];
    }
  }

  public async atualizar(idConta: number, conta: ContaPagar): Promise<void> {
    try {
      conta.id = idConta;
      await  axios.put(this.baseUrl, conta);
      console.log('Conta atualizada com sucesso');
    } catch (error) {
      console.error('Erro ao atualizar a Conta:', error);
    }
  }
}
