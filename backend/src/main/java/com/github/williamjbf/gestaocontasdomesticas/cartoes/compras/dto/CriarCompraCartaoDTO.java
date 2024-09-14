package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.Cartao;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class CriarCompraCartaoDTO implements Serializable {

    @NotNull
    private Long id;

    public Cartao toCartao() {
        final Cartao cartao = new Cartao();
        cartao.setId(this.id);
        return cartao;
    }

}
