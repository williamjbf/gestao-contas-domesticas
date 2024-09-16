package com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastosMensais {

    private String mesAno;
    private String categoria;
    private BigDecimal valor;

}
