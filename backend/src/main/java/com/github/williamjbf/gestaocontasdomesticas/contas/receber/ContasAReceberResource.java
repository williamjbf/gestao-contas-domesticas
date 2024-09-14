package com.github.williamjbf.gestaocontasdomesticas.contas.receber;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.receber.service.ContasAReceberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas/receber")
public class ContasAReceberResource {

    private final ContasAReceberService service;

    @Autowired
    public ContasAReceberResource(final ContasAReceberService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> criarConta(@Valid @RequestBody final ContaAReceberDTO contaDTO) {
        this.service.salvarConta(contaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        final List<Conta> contas = this.service.listarTodas();

        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contas);
    }

    @PutMapping
    public ResponseEntity atualizarConta(@Valid @RequestBody AtualizarContaAReceberDTO contaDTO) {

        final Conta conta = this.service.atualizarConta(contaDTO);

        return ResponseEntity.ok(conta);
    }

}
