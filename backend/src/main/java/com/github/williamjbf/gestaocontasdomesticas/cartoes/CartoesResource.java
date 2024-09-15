package com.github.williamjbf.gestaocontasdomesticas.cartoes;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.AtualizarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.CriarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.service.CartoesService;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/cartoes")
public class CartoesResource implements Serializable {

    private final CartoesService service;
    private final UsuarioService usuarioService;

    @Autowired
    public CartoesResource(final CartoesService service,
                           final UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity criarCartao(@Valid @RequestBody final CriarCartaoDTO cartaoDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        service.criarCartao(cartaoDTO, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity listarCartoes() {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final List<Cartao> cartoes = this.service.listarCartoes(idUsuario);

        if (cartoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cartoes);
    }

    @PutMapping
    public ResponseEntity editarCartao(@Valid @RequestBody final AtualizarCartaoDTO cartaoDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final Cartao cartao = this.service.editarCartao(cartaoDTO, idUsuario);

        return ResponseEntity.ok(cartao);
    }

}
