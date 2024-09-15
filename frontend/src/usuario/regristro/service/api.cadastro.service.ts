import {Injectable} from '@angular/core';
import axios from "axios";
import {Cadastro} from "../models/cadastro.model";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CadastroService {
  public apiURL = environment.apiURL;
  public baseURL = this.apiURL + "/auth/cadastro";
  private localStorageAuthTokenKey: string = 'Auth_JWT_Bearer_Token';

  constructor() { }

  public async cadastro(cadastro: Cadastro): Promise<void> {
    try {
      await axios.post(this.baseURL, cadastro);
      console.log('Cadastro realizado com sucesso');
    } catch (error) {
      console.error('Erro ao realizar Cadastro:', error);
      throw error; // Repassa o erro para o chamador
    }
  }

  public getToken(): string | null {
    return localStorage.getItem(this.localStorageAuthTokenKey);
  }

}
