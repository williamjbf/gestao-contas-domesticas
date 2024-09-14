package com.github.williamjbf.gestaocontasdomesticas.cartoes;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "t_compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_compra")
    private LocalDate dataCompra;
    private BigDecimal valor;
    private String descricao;
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_cartao", nullable = false)
    private Cartao cartao;
}
