package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.Cartao;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.Parcela;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidade que representa uma compra realizada em um cartão de crédito.
 * Cada compra possui uma data, valor, descrição, categoria, está associada a um cartão e
 * pode ser parcelada em várias parcelas. Além disso, a compra está vinculada a um usuário.
 */
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

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_cartao", nullable = false)
    private Cartao cartao;

    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "id_compra", nullable = false)
    private List<Parcela> parcelas;

    private boolean paga;

    private Long idUsuario;

}
