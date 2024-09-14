import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../validators/negative-value.validator/negative-value.validator";

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
    NgForOf,
    ReactiveFormsModule
  ],

  templateUrl: './contas-a-receber.component.html',
  styleUrl: './contas-a-receber.component.css',
  standalone: true
})
export class ContasAReceberComponent {
  contaForm: FormGroup;
  receitas: Receita[] = [];
  receita: Receita = {
    descricao: '',
    valor: 0,
    recebimento: '',
    status: 'pendente',
    categoria: ''
  };

  constructor(private fb: FormBuilder) {
    this.contaForm = this.fb.group({
      descricao: ['', Validators.required],
      valor: [0, [Validators.required, negativeValueValidator()]],
      recebimento: ['', Validators.required],
      status: ['', Validators.required],
      categoria: ['', Validators.required]
    });
  }

  addReceita() {
    if (this.contaForm.valid) {
      this.receitas.push({ ...this.receita });
      this.receita = { descricao: '', valor: 0, recebimento: '', status: 'pendente', categoria: '' };
    }
  }
}
