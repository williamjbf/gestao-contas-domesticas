package com.github.williamjbf.gestaocontasdomesticas.cartoes;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.AtualizarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.CriarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.service.CartoesService;
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

    @Autowired
    public CartoesResource(final CartoesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity criarCartao(@Valid @RequestBody final CriarCartaoDTO cartaoDTO) {

        service.criarCartao(cartaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity listarCartoes() {
        final List<Cartao> cartoes = this.service.listarCartoes();

        if (cartoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cartoes);
    }

    @PutMapping
    public ResponseEntity editarCartao(@Valid @RequestBody final AtualizarCartaoDTO cartaoDTO) {

        final Cartao cartao = this.service.editarCartao(cartaoDTO);

        return ResponseEntity.ok(cartao);
    }

}
