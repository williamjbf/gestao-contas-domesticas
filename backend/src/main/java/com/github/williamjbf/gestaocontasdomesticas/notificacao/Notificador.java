package com.github.williamjbf.gestaocontasdomesticas.notificacao;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Component
public class Notificador {

    private final Map<Long, Sinks.Many> listeners = new HashMap<>();

    public void inscrever(final Long user, final Sinks.Many listener) {
        listeners.put(user, listener);
    }

    public void notificar(final Long user, final Notificacao notificacao) {
        listeners.get(user).tryEmitNext(notificacao);
    }

}
