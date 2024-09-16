import { Component } from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {NgIf} from "@angular/common";
import {NotificacaoService} from "./notificacao/service/notificacao.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';

  constructor(private router: Router,
              private notificacaoService: NotificacaoService) {}

  isLoginPage(): boolean {
    return this.router.url === '/login' || this.router.url ==='/cadastro';
  }

  ngOnInit() {
    console.log(">>> Inscrevendo para receber notificações...")
    this.notificacaoService.getNotificacoes()
      .subscribe({
        next: notificacao => alert('notificacao: ' + notificacao.mensagem),
        error: err => console.error('Erro ao receber notificações', err)
      })
  }

}
