package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras;

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

    public static Parcela of(final BigDecimal valor, final LocalDate dataCobranca, final long ordem) {
        return new Parcela(null, valor, dataCobranca, ordem);
    }

}
