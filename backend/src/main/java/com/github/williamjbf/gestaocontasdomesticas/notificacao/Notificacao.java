package com.github.williamjbf.gestaocontasdomesticas.notificacao;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Notificacao {

    private final Long id;
    private final String mensagem;

}
