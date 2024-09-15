import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../../validators/negative-value.validator/negative-value.validator";
import {ContaReceber} from "./models/conta.receber.model";
import {ContasReceberService} from "./service/api.contas.receber.server";

@Component({
  selector: 'app-contas-a-receber',
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    ReactiveFormsModule,
    NgClass
  ],
  providers: [DatePipe],
  templateUrl: './contas.receber.component.html',
  styleUrl: './contas.receber.component.css',
  standalone: true
})
export class ContasReceberComponent {
  contaForm: FormGroup;
  receitas: ContaReceber[] = [];
  editingId: number | null = null;

  constructor(private fb: FormBuilder,
              private service: ContasReceberService,
              private datePipe: DatePipe) {
    this.contaForm = this.fb.group({
      descricao: ['', Validators.required],
      valor: [0, [Validators.required, negativeValueValidator()]],
      data: ['', Validators.required],
      status: ['', Validators.required],
      categoria: ['', Validators.required]
    });
  }

  async ngOnInit() {
    await this.loadContas();
  }

  async addOrUpdateConta(){
    if(this.contaForm.valid) {

      const formValue = this.contaForm.value;
      formValue.data = this.datePipe.transform(formValue.data, 'dd/MM/yyyy')

      try {
        if (this.editingId === null){
          await this.service.cadastrar(this.contaForm.value);
        } else {
          await this.service.atualizar(this.editingId, this.contaForm.value);
          this.editingId = null;
        }
        this.contaForm.reset();
        await this.loadContas();
      } catch (error){
        console.error('Erro ao cadastrar conta:', error);
      }
    }
  }

  async loadContas() {
    try {
      this.receitas = await this.service.listar();
    } catch (error) {
      console.error('Erro ao carregar contas:', error);
    }
  }

  editarConta(conta: ContaReceber) {
    console.log("conta id"+ conta.id);
    this.editingId = conta.id;
    console.log(this.editingId);
    console.log("conta id"+ conta.id);
    this.contaForm.patchValue(conta);
  }

  formatarData(data: string): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy') ?? data;
  }
}
