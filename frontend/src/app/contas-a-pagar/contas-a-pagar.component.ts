import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

interface ContaPagar {
  descricao: string;
  valor: number;
  vencimento: string;
  status: string;
  categoria: string;
}

@Component({
  selector: 'app-contas-a-pagar',
  standalone: true,
  templateUrl: './contas-a-pagar.component.html',
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  styleUrls: ['./contas-a-pagar.component.css']
})
export class ContasAPagarComponent {
  conta: ContaPagar = {
    descricao: '',
    valor: 0,
    vencimento: '',
    status: 'pendente',
    categoria: ''
  };

  contas: ContaPagar[] = [];

  addContaPagar() {
    this.contas.push({ ...this.conta });
    this.conta = { descricao: '', valor: 0, vencimento: '', status: 'pendente', categoria: '' };
  }
}
