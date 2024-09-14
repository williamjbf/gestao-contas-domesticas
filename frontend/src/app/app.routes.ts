import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContasAPagarComponent } from './contas-a-pagar/contas-a-pagar.component';
import { ContasAReceberComponent } from './contas-a-receber/contas-a-receber.component';
import { ComprasCartoesComponent } from './cartoes/compras/compras-cartoes.component';
import {CartaoComponent} from "./cartoes/cartao.component";

export const routes: Routes = [
  { path: 'contas-a-pagar', component: ContasAPagarComponent },
  { path: 'contas-a-receber', component: ContasAReceberComponent },
  { path: 'cartoes', component: CartaoComponent },
  { path: 'cartoes/compras/:id_cartao', component: ComprasCartoesComponent },
  { path: '', redirectTo: '/contas-a-pagar', pathMatch: 'full' }
];
