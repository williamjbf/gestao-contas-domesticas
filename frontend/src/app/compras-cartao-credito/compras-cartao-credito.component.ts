import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

interface ComprasCartaoCredito {
  descricao: string,
  valor: number,
  dataCompra: string,
  categoria: string,
  cartao: number
}

@Component({
  selector: 'app-compras-cartao-credito',
  standalone: true,
  templateUrl: './compras-cartao-credito.component.html',
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  styleUrls: ['./compras-cartao-credito.component.css']
})
export class ComprasCartaoCreditoComponent {
  compra: ComprasCartaoCredito= {
    descricao: '',
    valor: 0,
    dataCompra: '',
    categoria: '',
    cartao: 0
  };

  compras: ComprasCartaoCredito[] = [];

  addCompra() {
    this.compras.push({...this.compra});
    this.compra = {descricao: '', valor: 0, dataCompra: '', categoria: '', cartao: 0};
  }
}
