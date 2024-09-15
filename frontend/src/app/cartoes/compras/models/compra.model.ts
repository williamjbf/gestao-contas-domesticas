import {Cartao} from "../../models/cartao.model";

export interface Compra {
  id: number | null,
  descricao: string,
  valor: number,
  quantidadeParcelas: number,
  dataCompra: string,
  categoria: string,
  cartao: Cartao,
  parcelas: Parcela[]
}

export interface Parcela {
  id: number | null,
  valor: number,
  dataCobranca: string,
  ordem: number
}
