package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.dto.in.StatusPagamento;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.service.ParcelaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes/compras/parcelas")
public class ParcelaResource {

    private final ParcelaService parcelaService;

    @Autowired
    public ParcelaResource(final ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    @PutMapping("/{idParcela}")
    public ResponseEntity<Void> definirPagamento(@PathVariable(name = "idParcela") final Long idParcela,
                                                 @Valid @RequestBody final StatusPagamento statusPagamento) {
        this.parcelaService.definirPagamento(idParcela, statusPagamento);
        return ResponseEntity.ok().build();
    }

}
