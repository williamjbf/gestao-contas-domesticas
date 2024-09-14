import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../validators/negative-value.validator/negative-value.validator";

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
    NgForOf,
    ReactiveFormsModule
  ],
  styleUrls: ['./contas-a-pagar.component.css']
})
export class ContasAPagarComponent {
  contaForm: FormGroup;
  contas: ContaPagar[] = [];
  conta: ContaPagar = {
    descricao: '',
    valor: 0,
    vencimento: '',
    status: 'pendente',
    categoria: ''
  };

  constructor(private fb: FormBuilder) {
    this.contaForm = this.fb.group({
      descricao: ['', Validators.required],
      valor: [0, [Validators.required, negativeValueValidator()]],
      vencimento: ['', Validators.required],
      status: ['', Validators.required],
      categoria: ['', Validators.required]
    });
  }

  addContaPagar() {
    if (this.contaForm.valid) {
      this.contas.push({ ...this.conta });
      this.conta = { descricao: '', valor: 0, vencimento: '', status: 'pendente', categoria: '' };
    }
  }
}
