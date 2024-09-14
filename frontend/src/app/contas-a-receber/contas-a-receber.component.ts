import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

interface Receita {
  descricao: string;
  valor: number;
  recebimento: string;
  status: string;
  categoria: string;
}

@Component({
  selector: 'app-contas-a-receber',
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],

  templateUrl: './contas-a-receber.component.html',
  styleUrl: './contas-a-receber.component.css',
  standalone: true
})
export class ContasAReceberComponent {
  receita: Receita = {
    descricao: '',
    valor: 0,
    recebimento: '',
    status: 'pendente',
    categoria: ''
  };

  receitas: Receita[] = [];

  addReceita() {
    console.log(this.receitas)
    this.receitas.push({ ...this.receita });
    this.receita = { descricao: '', valor: 0, recebimento: '', status: 'pendente', categoria: '' };
  }
}
