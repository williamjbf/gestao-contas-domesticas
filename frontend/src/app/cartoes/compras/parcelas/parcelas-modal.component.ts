import { Component, Input, Output, EventEmitter } from '@angular/core';
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {ParcelaService} from "./service/api.parcelas.service";
import {Parcela} from "./models/parcela.model";

@Component({
  selector: 'app-parcelas-modal',
  templateUrl: './parcelas-modal.component.html',
  styleUrls: ['./parcelas-modal.component.css'],
  imports: [
    NgIf,
    NgForOf,
    NgClass
  ],
  standalone: true,
  providers: [DatePipe]
})
export class ParcelasModalComponent {
  @Input() parcelas: Parcela[] = [];
  @Input() showModal: boolean = false;
  @Output() closeModalEvent = new EventEmitter<void>();

  constructor(private datePipe: DatePipe,
              private service: ParcelaService) { }

  closeModal() {
    this.closeModalEvent.emit();
  }

  async marcarPaga(parcela: Parcela) {
    try {
      await this.service.alterarStatusPagamento(parcela.id, {
        status: 'PAGO'
      });
      parcela.paga = true;
    } catch (error) {
      console.error('Erro ao atualizar Parcela:', error);
    }
  }

  async marcarNaoPaga(parcela: Parcela) {
    try {
      await this.service.alterarStatusPagamento(parcela.id, {
        status: 'NAO_PAGO'
      });
      parcela.paga = false;
    } catch (error) {
      console.error('Erro ao atualizar Parcela:', error);
    }
  }

  formatarData(data: string): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy') ?? data;
  }

  isVencida(parcela: Parcela): boolean {
    if (parcela.paga) return false;

    const hoje = new Date();
    return new Date(parcela.dataCobranca) <= hoje;
  }

}
