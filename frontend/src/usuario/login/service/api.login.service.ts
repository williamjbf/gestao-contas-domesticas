import { Injectable } from '@angular/core';
import axios from "axios";
import {Login} from "../models/login.model";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public apiURL = environment.apiURL;
  public baseURL = this.apiURL + "/auth/login";
  private localStorageAuthTokenKey: string = 'Auth_JWT_Bearer_Token';

  constructor() { }

  public async login(login: Login): Promise<void> {
    try {
      const response = await axios.post(this.baseURL, login);
      console.log('Login realizado com sucesso');

      /*
        Setta, no LocalStorage, o JWT_Bearer_Token vindo no body da request
        de login para ser usado futuramente nas proximas requests
       */
      const token = response.data.token;
      localStorage.setItem(this.localStorageAuthTokenKey, token)
    } catch (error) {
      console.error('Erro ao realizar Login:', error);
      throw error; // Repassa o erro para o chamador
    }
  }

  public getToken(): string | null {
    return localStorage.getItem(this.localStorageAuthTokenKey);
  }

}
