import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../../validators/negative-value.validator/negative-value.validator";
import {ContaPagar} from "./models/conta.pagar.model";
import {ContasPagarService} from "./service/api.contas.pagar.servite";
import {NotificacaoService} from "../../notificacao/service/notificacao.service";
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';

@Component({
  selector: 'app-contas-a-pagar',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    ReactiveFormsModule,
    NgClass,
    MatInputModule,
    MatSelectModule
  ],
  providers: [DatePipe],
  templateUrl: './contas.pagar.component.html',
  styleUrls: ['./contas.pagar.component.css']
})
export class ContasPagarComponent {
  contaForm: FormGroup;
  contas: ContaPagar[] = [];
  editingId: number | null = null;
  data: any;

  constructor(private fb: FormBuilder,
              private service: ContasPagarService,
              private datePipe: DatePipe,
              private notificacaoService: NotificacaoService) {
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
    console.log(">>> Inscrevendo para receber notificações...")
    this.notificacaoService.getNotificacoes()
      .subscribe({
        next: notificacao => alert('notificacao: ' + notificacao.mensagem),
        error: err => console.error('Erro ao receber notificações', err)
      })
  }

  async addOrUpdateConta() {
    if (this.contaForm.valid) {

      const formValue = this.contaForm.value;
      formValue.data = this.datePipe.transform(formValue.data, 'dd/MM/yyyy')

      try {
        if (this.editingId === null) {
          await this.service.cadastrar(this.contaForm.value);
        } else {
          await this.service.atualizar(this.editingId, this.contaForm.value);
          this.editingId = null;
        }
        this.contaForm.reset();
        await this.loadContas();
      } catch (error) {
        console.error('Erro ao cadastrar conta:', error);
      }
    }
  }

  async loadContas() {
    try {
      this.contas = await this.service.listar();
    } catch (error) {
      console.error('Erro ao carregar contas:', error);
    }
  }

  editarConta(conta: ContaPagar) {
    console.log("conta id" + conta.id);
    this.editingId = conta.id;
    console.log(this.editingId);
    console.log("conta id" + conta.id);
    this.contaForm.patchValue(conta);
  }

  formatarData(data: string): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy') ?? data;
  }

}
