import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../validators/negative-value.validator/negative-value.validator";
import {CartaoService} from "./api.cartoes.service";
import {Router} from "@angular/router";


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
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './cartao.component.html',
  styleUrl: './cartao.component.css'
})
export class CartaoComponent {
  cartaoForm: FormGroup;
  cartoes: Cartao[] = [];
  editingId: number | null = null;

  constructor(private fb: FormBuilder,
              private service: CartaoService,
              private router: Router) {
    this.cartaoForm = this.fb.group({
      limite: [0, [Validators.required, negativeValueValidator()]],
      descricao: ['', Validators.required]
    });
  }

  async ngOnInit() {
    await this.loadCartoes();
  }

  async addOrUpdateCartao() {
    if (this.cartaoForm.valid) {
      try {
        console.log(this.cartaoForm.value);
        console.log(this.editingId);
        if (this.editingId === null) {
          await this.service.cadastrar(this.cartaoForm.value);
        } else {
          await this.service.atualizar(this.editingId, this.cartaoForm.value);
          this.editingId = null;
        }
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

  editarCartao(cartao: Cartao) {
    console.log("cartao id"+ cartao.id);
    this.editingId = cartao.id;
    console.log(this.editingId);
    console.log("cartao id"+ cartao.id);
    this.cartaoForm.patchValue(cartao);
  }

  verCompras(cartao: Cartao): void {
    this.router.navigate([`/cartoes/compras/${cartao.id}`]);
  }

}
