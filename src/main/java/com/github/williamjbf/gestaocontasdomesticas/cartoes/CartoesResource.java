package com.github.williamjbf.gestaocontasdomesticas.cartoes;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.CriarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.service.CartoesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

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
}
