// src/app/interceptors/axios.interceptor.ts
import axios from 'axios';
import {environment} from "../../environments/environment";

const apiURL = environment.apiURL;
// todo: criar uma variavel de ambiente para centralizar esta key
const localStorageAuthTokenKey = 'Auth_JWT_Bearer_Token';

axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem(localStorageAuthTokenKey);
    if (token) {
      config.headers['Authorization'] = token;
    }
    config.baseURL = apiURL;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axios;
