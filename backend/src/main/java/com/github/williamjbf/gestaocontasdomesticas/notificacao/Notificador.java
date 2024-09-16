package com.github.williamjbf.gestaocontasdomesticas.notificacao;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.*;

/**
 * O `Notificador` é um componente responsável por gerenciar e distribuir notificações para usuários.
 *
 * RF01: O Notificador envia as notificacoes para todos os clients configurados do usuario (List<Sinks.Many<Notificacao>>)
 * <br/>
 * RF02: O Notificador garante que as notificações são enviadas apenas uma vez (ignorando tentativas  de notificações repetidas)
 * mas para todos os clients configurados do usuario, segundo RF01.
 */
@Component
public class Notificador {

    // Mapeia uma lista de clients (value do Map = List<Sinks.Many<Notificacao>>) para um user (key do Map = Long)
    private final Map<Long, List<Sinks.Many<Notificacao>>> listeners = new HashMap<>();

    // Mapeia uma conjunto de notificacoes (value do map = Set<Notificacao>) que já foram enviados para o usuario (key do Map = Long)
    private final Map<Long, Set<Notificacao>> notificacoes = new HashMap<>();

    public void inscrever(final Long user, final Sinks.Many<Notificacao> listener) {
        listeners.computeIfAbsent(user, k -> new ArrayList<>()).add(listener);
        notificacoes.computeIfAbsent(user, key -> new HashSet<>());
    }

    public void notificar(final Long user, final Notificacao notificacao) {
        final List<Sinks.Many<Notificacao>> userNotifiers = listeners.get(user);

        final Set<Notificacao> notificacoesDoUsuario = notificacoes.get(user);

        if (userNotifiers != null && !notificacoesDoUsuario.contains(notificacao)) {
            for (Sinks.Many<Notificacao> userNotifier : userNotifiers) {
                userNotifier.tryEmitNext(notificacao);
            }
            notificacoesDoUsuario.add(notificacao);
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