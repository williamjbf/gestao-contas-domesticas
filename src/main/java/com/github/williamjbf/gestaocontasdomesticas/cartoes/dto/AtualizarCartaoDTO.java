package com.github.williamjbf.gestaocontasdomesticas.cartoes.dto;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.Cartao;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AtualizarCartaoDTO implements Serializable {

    private Long id;
    private String descricao;
    private BigDecimal limite;

    public Cartao toCartao(){
        final Cartao cartao = new Cartao();

        cartao.setId(this.id);
        cartao.setDescricao(this.descricao);
        cartao.setLimite(this.limite);

        return cartao;
    }
}
