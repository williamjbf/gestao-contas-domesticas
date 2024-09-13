package com.github.williamjbf.gestaocontasdomesticas.contas.pagar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.williamjbf.gestaocontasdomesticas.contas.Categoria;
import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.Status;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContaAPagarDTO implements Serializable {

    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal valor;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
    @NotNull
    private Status status;
    @NotNull
    private Categoria categoria;

    public Conta toConta(){

        Conta conta = new Conta();
        conta.setDescricao(this.descricao);
        conta.setValor(this.valor);
        conta.setData(this.data);
        conta.setStatus(this.status);
        conta.setCategoria(this.categoria);
        conta.setTipoConta(TipoConta.CONTA_A_PAGAR);

        return conta;
    }
}
