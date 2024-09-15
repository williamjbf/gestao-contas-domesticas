import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { DatePipe, NgForOf, NgIf } from "@angular/common";
import { negativeValueValidator } from "../../validators/negative-value.validator/negative-value.validator";
import { ComprasService } from "./service/api.compras.service";
import { Cartao } from "../models/cartao.model";
import { Compra } from "./models/compra.model";
import { CartaoService } from "../api.cartoes.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-compras-cartao-credito',
  standalone: true,
  templateUrl: './compras-cartoes.component.html',
  styleUrls: ['./compras-cartoes.css'],
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    ReactiveFormsModule
  ],
  providers: [DatePipe]
})
export class ComprasCartoesComponent {
  compraForm: FormGroup;
  compras: Compra[] = [];
  cartoes: Cartao[] = [];
  editingId: number | null = null;
  idActualCartao: number | null = null;

  constructor(private fb: FormBuilder,
              private service: ComprasService,
              private cartaoService: CartaoService,
              private datePipe: DatePipe,
              private route: ActivatedRoute) {
    this.compraForm = this.fb.group({
      descricao: ['', Validators.required],
      valor: [null, [Validators.required, negativeValueValidator()]],
      dataCompra: ['', Validators.required],
      categoria: ['', Validators.required],
      cartao: [this.idActualCartao, Validators.required]
    });
  }

  async ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.idActualCartao = Number(params.get('id_cartao'));
     this.setDefaultCartao();
    });

    await this.loadCompras();
    await this.loadCartoes();
  }

  async addOrUpdateCompra() {
    if (this.compraForm.valid) {
      const formValue = this.compraForm.value;
      formValue.dataCompra = this.datePipe.transform(formValue.dataCompra, 'dd/MM/yyyy');

      formValue.cartao = {
        id: formValue.cartao
      };

      try {
        if (this.editingId === null) {
          await this.service.cadastrar(formValue);
        } else {
          await this.service.atualizar(this.editingId, formValue);
          this.editingId = null;
        }
        this.compraForm.reset();
        this.setDefaultCartao();
        await this.loadCompras();
      } catch (error) {
        console.error('Erro ao cadastrar Compra:', error);
      }
    }
  }

  async loadCompras() {
    try {
      console.log("id do cartao: "+ this.idActualCartao);
      this.compras = await this.service.listar(this.idActualCartao);
    } catch (error) {
      console.error('Erro ao carregar Compras:', error);
    }
  }

  async loadCartoes() {
    try {
      this.cartoes = await this.cartaoService.listar();
    } catch (error) {
      console.error('Erro ao carregar Cartões:', error);
    }
  }

  editarCompra(compra: Compra) {
    this.editingId = compra.id;
    this.compraForm.patchValue({
      ...compra,
      cartao: compra.cartao.id
    });
  }

  formatarData(data: string): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy') ?? data;
  }

  setDefaultCartao() {
    this.compraForm.patchValue({
      cartao: this.idActualCartao
    });
  }

}
