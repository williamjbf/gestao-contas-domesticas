import {Injectable} from "@angular/core";
import {environment} from "../../../../environments/environment";
import {ContaReceber} from "../models/conta.receber.model";
import axios from '../../../interceptors/axios.interceptor';

@Injectable({
  providedIn: 'root'
})
export class ContasReceberService {
  public apiUrl = environment.apiURL;
  public baseUrl = this.apiUrl + "/contas/receber";

  constructor() {
  }

  public async cadastrar(contaReceber: ContaReceber): Promise<void> {
    try {
      await axios.post(this.baseUrl, contaReceber);
      console.log('Conta a Receber cadastrada com sucesso')
    } catch (error) {
      console.error('Erro ao cadastrar a conta a Receber:', error);
    }
  }

  public async listar(): Promise<ContaReceber[]>{
    return this.listarTodas();
  }

  public async listarTodas(): Promise<ContaReceber[]>{
    try {
      const response = await axios.get(this.baseUrl);
      return response.data;
    } catch (error) {
      console.error('Erro ao listar Todas as Contas a Receber:', error);
      return [];
    }
  }

  public async atualizar(idConta: number, conta: ContaReceber): Promise<void> {
    try {
      conta.id = idConta;
      await  axios.put(this.baseUrl, conta);
      console.log('Conta atualizada com sucesso');
    } catch (error) {
      console.error('Erro ao atualizar a Conta:', error);
    }
  }
}
