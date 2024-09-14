package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.CriarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartoes/compras")
public class ComprasResource {

    private final CompraService service;

    @Autowired
    public ComprasResource(final CompraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity adicionarCompra(@Valid @RequestBody final CriarCompraDTO compraDTO) {

        service.adicionarCompra(compraDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity listarCompras(){
        final List<Compra> compras = service.listarCompras();

        if(compras.isEmpty()){
            ResponseEntity.noContent();
        }

        return ResponseEntity.ok(compras);
    }
}
