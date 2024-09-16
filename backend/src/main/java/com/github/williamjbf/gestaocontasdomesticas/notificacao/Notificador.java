package com.github.williamjbf.gestaocontasdomesticas.notificacao;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.*;

/**
 * O `Notificador` é um componente responsável por gerenciar e distribuir notificações para usuários.
 */
@Component
public class Notificador {

    private final Map<Long, Sinks.Many> listeners = new HashMap<>();
    private final Map<Long, Set<Notificacao>> notificacoes = new HashMap<>();

    public void inscrever(final Long user, final Sinks.Many listener) {
        listeners.put(user, listener);
        notificacoes.computeIfAbsent(user, key -> new HashSet<>());
    }

    public void notificar(final Long user, final Notificacao notificacao) {
        final Sinks.Many userNotifier = listeners.get(user);

        final Set<Notificacao> notificacoesDoUsuario = notificacoes.get(user);

        if (userNotifier != null && ! notificacoesDoUsuario.contains(notificacao)) {
            userNotifier.tryEmitNext(notificacao);
            notificacoes.get(user).add(notificacao);
        }

    }

    /**
     * Executa a cada 12h
     */
    @Scheduled(cron = "0 0 */12 * * *")
    public void limparTodasAsNotificacoes() {
        notificacoes.clear();
    }

}
