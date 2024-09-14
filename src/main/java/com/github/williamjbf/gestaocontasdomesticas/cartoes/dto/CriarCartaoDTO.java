package com.github.williamjbf.gestaocontasdomesticas.cartoes.dto;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.Cartao;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CriarCartaoDTO {

    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal limite;

    public Cartao toCartao() {
        final Cartao cartao = new Cartao();

        cartao.setDescricao(this.descricao);
        cartao.setLimite(this.limite);

        return cartao;
    }
}
