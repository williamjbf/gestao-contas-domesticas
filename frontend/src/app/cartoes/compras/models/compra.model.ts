import {Cartao} from "../../models/cartao.model";
import {Parcela} from "../parcelas/models/parcela.model";

export interface Compra {
  id: number | null,
  descricao: string,
  valor: number,
  quantidadeParcelas: number,
  dataCompra: string,
  categoria: string,
  cartao: Cartao,
  parcelas: Parcela[],
  paga: boolean
}
