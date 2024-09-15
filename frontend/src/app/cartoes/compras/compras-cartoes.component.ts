import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import { negativeValueValidator } from "../../validators/negative-value.validator/negative-value.validator";
import { ComprasService } from "./service/api.compras.service";
import { Cartao } from "../models/cartao.model";
import { Compra } from "./models/compra.model";
import { CartaoService } from "../api.cartoes.service";
import {ActivatedRoute} from "@angular/router";
import {ParcelasModalComponent} from "./parcelas/parcelas-modal.component";
import {Parcela} from "./parcelas/models/parcela.model";

@Component({
  selector: 'app-compras-cartao-credito',
  standalone: true,
  templateUrl: './compras-cartoes.component.html',
  styleUrls: ['./compras-cartoes.css'],
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    ReactiveFormsModule,
    ParcelasModalComponent,
    NgClass
  ],
  providers: [DatePipe]
})
export class ComprasCartoesComponent {
  compraForm: FormGroup;
  compras: Compra[] = [];
  cartoes: Cartao[] = [];
  editingId: number | null = null;
  idActualCartao: number | null = null;
  showModal: boolean = false;
  parcelas: any[] = [];

  constructor(private fb: FormBuilder,
              private service: ComprasService,
              private cartaoService: CartaoService,
              private datePipe: DatePipe,
              private route: ActivatedRoute) {
    this.compraForm = this.fb.group({
      descricao: ['', Validators.required],
      valor: [null, [Validators.required, negativeValueValidator()]],
      quantidadeParcelas: [0, []],
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
      quantidadeParcelas: compra.parcelas.length,
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

  quantidadeParcelasPagas(parcelas: Parcela[]): number {
    return parcelas.filter(parcela => parcela.paga).length;
  }

  async verParcelas(compra: Compra) {
    // Ordena as parcelas com base no atributo ordem
    this.parcelas = compra.parcelas.sort((a, b) => a.ordem - b.ordem);
    this.showModal = true;
  }

  /*
    A compra está vencida de uma de suas Parcelas estiver vencida
   */
  isVencida(compra: Compra): boolean {
    if (compra.paga) return false;

    const hoje = new Date();
    return compra.parcelas.some(parcela => new Date(parcela.dataCobranca) <= hoje && !parcela.paga);
  }

  async closeModal() {
    this.showModal = false;
    await this.loadCompras();
  }

  async marcarPaga(compra: Compra) {
    try {
      await this.service.alterarStatusPagamento(compra.id, {
        status: 'PAGO'
      });
      await this.loadCompras();
    } catch (error) {
      console.error('Erro ao atualizar Compra:', error);
    }
  }

  async marcarNaoPaga(compra: Compra) {
    try {
      await this.service.alterarStatusPagamento(compra.id, {
        status: 'NAO_PAGO'
      });
      await this.loadCompras();
    } catch (error) {
      console.error('Erro ao atualizar Compra:', error);
    }
  }

}
