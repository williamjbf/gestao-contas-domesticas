package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.dto.in.StatusPagamento;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.service.ParcelaService;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartoes/compras/parcelas")
public class ParcelaResource {

    private final ParcelaService parcelaService;
    private final UsuarioService usuarioService;

    @Autowired
    public ParcelaResource(final ParcelaService parcelaService,
                           final UsuarioService usuarioService) {
        this.parcelaService = parcelaService;
        this.usuarioService = usuarioService;
    }

    @PutMapping("/{idParcela}")
    public ResponseEntity<Void> definirPagamento(@PathVariable(name = "idParcela") final Long idParcela,
                                                 @Valid @RequestBody final StatusPagamento statusPagamento) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        this.parcelaService.definirPagamento(idParcela, statusPagamento, idUsuario);
        return ResponseEntity.ok().build();
    }

}
