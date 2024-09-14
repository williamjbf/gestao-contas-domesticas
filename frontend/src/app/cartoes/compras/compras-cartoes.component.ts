import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {negativeValueValidator} from "../../validators/negative-value.validator/negative-value.validator";

interface ComprasCartaoCredito {
  descricao: string,
  valor: number,
  dataCompra: string,
  categoria: string,
  cartao: number
}

@Component({
  selector: 'app-compras-cartao-credito',
  standalone: true,
  templateUrl: './compras-cartoes.component.html',
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    ReactiveFormsModule
  ],
  styleUrls: ['./compras-cartoes.css']
})
export class ComprasCartoesComponent {
  compraForm: FormGroup;
  compras: ComprasCartaoCredito[] = [];

  constructor(private fb: FormBuilder) {
    this.compraForm = this.fb.group({
      descricao: ['', Validators.required],
      valor: [0, [Validators.required, negativeValueValidator()]],
      dataCompra: ['', Validators.required],
      categoria: ['', Validators.required],
      cartao: ['', Validators.required]
    });
  }

  addCompra() {
    if (this.compraForm.valid) {
      this.compras.push({...this.compraForm.value});
      this.compraForm.reset();
    }
  }
}
