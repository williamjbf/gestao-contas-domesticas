package com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.notificacao.Notificacao;
import com.github.williamjbf.gestaocontasdomesticas.notificacao.Notificador;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.Usuario;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CriadorNotificacaoContasProximasAoVencimento {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Long diasParaVencimento = 2L;

    private final ContasAPagarService contasAPagarService;
    private final UsuarioService usuarioService;
    private final Notificador notificador;

    @Autowired
    public CriadorNotificacaoContasProximasAoVencimento(final ContasAPagarService contasAPagarService,
                                                        final UsuarioService usuarioService,
                                                        final Notificador notificador) {
        this.contasAPagarService = contasAPagarService;
        this.usuarioService = usuarioService;
        this.notificador = notificador;
    }

    @Scheduled(fixedDelay = 600000)
    public void criarNotificacoesProximasAoVencimento() {

        final Usuario usuarioLogado = usuarioService.getUsuarioLogado();
        final Long idUsuario = usuarioLogado != null ? usuarioLogado.getId() : -1L;

        contasAPagarService.listarContasProximasAoVencimento(diasParaVencimento, idUsuario).stream()
                .map(this::criarMensagemNotificacaoConta)
                .map(mensagem -> Notificacao.builder().mensagem(mensagem).build())
                .forEach(notificacao -> notificador.notificar(idUsuario, notificacao));
    }

    private String criarMensagemNotificacaoConta(final Conta conta) {
        final String message = String.format("Efetue o pagamento de %s com valor de %s antes do dia %s",
                conta.getDescricao(),
                conta.getValor(),
                conta.getData().format(dateTimeFormatter)
        );

        return message;
    }
}