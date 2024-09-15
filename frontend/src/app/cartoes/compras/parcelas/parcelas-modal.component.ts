import { Component, Input, Output, EventEmitter } from '@angular/core';
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-parcelas-modal',
  templateUrl: './parcelas-modal.component.html',
  styleUrls: ['./parcelas-modal.component.css'],
  imports: [
    NgIf,
    NgForOf
  ],
  standalone: true,
  providers: [DatePipe]
})
export class ParcelasModalComponent {
  @Input() parcelas: any[] = [];
  @Input() showModal: boolean = false;
  @Output() closeModalEvent = new EventEmitter<void>();

  constructor(private datePipe: DatePipe) { }

  closeModal() {
    this.closeModalEvent.emit();
  }

  formatarData(data: string): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy') ?? data;
  }

}
