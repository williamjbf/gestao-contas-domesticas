import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../validators/negative-value.validator/negative-value.validator";
import {CartaoService} from "../cartoes/api.service";


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

  constructor(private fb: FormBuilder,
              private service: CartaoService) {
    this.cartaoForm = this.fb.group({
      limite: [0, [Validators.required, negativeValueValidator()]],
      descricao: ['', Validators.required]
    });
  }

  async ngOnInit() {
    await this.loadCartoes();
  }

  async addCartao() {
    if (this.cartaoForm.valid) {
      try {
        await this.service.cadastrar(this.cartaoForm.value);
        this.cartaoForm.reset();
        await this.loadCartoes();
      } catch (error) {
        console.error('Erro ao cadastrar cartão:', error);
      }
    }
  }

  async loadCartoes() {
    try {
      this.cartoes = await this.service.listar();
    } catch (error) {
      console.error('Erro ao carregar cartões:', error);
    }
  }
}
