import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../validators/negative-value.validator/negative-value.validator";

interface Cartao {
  id: number;
  limite: number;
  descricao: string;
}

@Component({
  selector: 'app-cadastrar-cartao',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './cadastrar-cartao.component.html',
  styleUrl: './cadastrar-cartao.component.css'
})
export class CadastrarCartaoComponent {
  cartaoForm: FormGroup;
  cartoes: Cartao[] = [];

  constructor(private fb: FormBuilder) {
    this.cartaoForm = this.fb.group({
      limite: [0, [Validators.required, negativeValueValidator()]],
      descricao: ['', Validators.required]
    });
  }

  addCartao() {
    if (this.cartaoForm.valid) {
      this.cartoes.push({ ...this.cartaoForm.value });
      this.cartaoForm.reset();
    }
  }
}
