package com.github.williamjbf.gestaocontasdomesticas.contas.pagar;

import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.ContasAPagarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contas/pagar")
public class ContasAPagarResource {

    @Autowired
    private ContasAPagarService service;

    @PostMapping
    public ResponseEntity criarConta(@Valid @RequestBody final ContaAPagarDTO contaAPagarDTO){

        service.salvarConta(contaAPagarDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
