package com.github.williamjbf.gestaocontasdomesticas.contas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "t_conta")
public class Conta implements Serializable {

    @Id
    private Long id;

    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Status status;
    private Categoria categoria;
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;

}
