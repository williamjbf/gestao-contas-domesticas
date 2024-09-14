package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Categoria;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Compra;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CriarCompraDTO implements Serializable {

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCompra;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private String descricao;
    @NotNull
    private Categoria categoria;

    @Valid
    @NotNull
    private CriarCompraCartaoDTO cartao;

    public Compra toCompra() {
        final Compra compra = new Compra();

        compra.setDataCompra(this.dataCompra);
        compra.setValor(this.valor);
        compra.setDescricao(this.descricao);
        compra.setCategoria(this.categoria);
        compra.setCartao(this.cartao.toCartao());

        return compra;
    }
}
