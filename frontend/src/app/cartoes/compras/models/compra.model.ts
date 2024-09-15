import {Cartao} from "../../models/cartao.model";

export interface Compra {
  id: number | null,
  descricao: string,
  valor: number,
  dataCompra: string,
  categoria: string,
  cartao: Cartao
}
