package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.CriarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.EditarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.service.CompraService;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartoes")
public class ComprasResource {

    private final CompraService service;
    private final UsuarioService usuarioService;

    @Autowired
    public ComprasResource(final CompraService service,
                           final UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/compras")
    public ResponseEntity adicionarCompra(@Valid @RequestBody final CriarCompraDTO compraDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        service.adicionarCompra(compraDTO, idUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/compras")
    public ResponseEntity listarCompras() {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final List<Compra> compras = service.listarCompras(idUsuario);

        if (compras.isEmpty()) {
            ResponseEntity.noContent();
        }

        return ResponseEntity.ok(compras);
    }

    @PutMapping("/compras")
    public ResponseEntity editarCompra(@Valid @RequestBody final EditarCompraDTO compraDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final Compra compra = service.editarCompra(compraDTO, idUsuario);

        return ResponseEntity.ok(compra);
    }


    @GetMapping("/{idCartao}/compras")
    public ResponseEntity<List<Compra>> buscarComprasPorCartao(@PathVariable(name = "idCartao") final Long idCartao) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final List<Compra> compras = service.buscarComprasPorCartao(idCartao, idUsuario);

        if (compras.isEmpty()) {
            ResponseEntity.noContent();
        }

        return ResponseEntity.ok(compras);
    }
    
}
