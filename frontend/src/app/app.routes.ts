import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContasAPagarComponent } from './contas-a-pagar/contas-a-pagar.component';
import { ContasAReceberComponent } from './contas-a-receber/contas-a-receber.component';
import { ComprasCartaoCreditoComponent } from './compras-cartao-credito/compras-cartao-credito.component';

export const routes: Routes = [
  { path: 'contas-a-pagar', component: ContasAPagarComponent },
  { path: 'contas-a-receber', component: ContasAReceberComponent },
  { path: 'compras-cartao-credito', component: ComprasCartaoCreditoComponent },
  { path: '', redirectTo: '/contas-a-pagar', pathMatch: 'full' }
];
