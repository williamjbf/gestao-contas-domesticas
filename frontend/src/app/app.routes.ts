import {Routes} from '@angular/router';
import {ContasPagarComponent} from './contas/pagar/contas.pagar.component';
import {ContasReceberComponent} from './contas/receber/contas.receber.component';
import {ComprasCartoesComponent} from './cartoes/compras/compras-cartoes.component';
import {CartaoComponent} from "./cartoes/cartao.component";
import {LoginComponent} from "./login/login.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'contas-a-pagar', component: ContasPagarComponent },
  { path: 'contas-a-receber', component: ContasReceberComponent },
  { path: 'cartoes', component: CartaoComponent },
  { path: 'cartoes/compras/:id_cartao', component: ComprasCartoesComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];
