package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "t_parcela")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    @Column(name = "data_cobranca")
    private LocalDate dataCobranca;

    private long ordem;

    private boolean paga;

    private Long idUsuario;

    public static Parcela of(final BigDecimal valor, final LocalDate dataCobranca, final long ordem, final Long idUsuario) {
        return new Parcela(null, valor, dataCobranca, ordem, false, idUsuario);
    }

}
