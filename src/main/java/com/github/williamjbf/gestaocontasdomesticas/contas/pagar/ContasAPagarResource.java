package com.github.williamjbf.gestaocontasdomesticas.contas.pagar;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.ContasAPagarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas/pagar")
public class ContasAPagarResource {

    private final ContasAPagarService service;

    @Autowired
    public ContasAPagarResource(final ContasAPagarService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> criarConta(@Valid @RequestBody final ContaAPagarDTO contaDTO) {
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

    @GetMapping("/proximas/vencimento")
    public ResponseEntity<List<Conta>> listarContasProximasAoVencimento(
            @RequestParam(required = false, defaultValue = "2") final Long diasParaVencimento) {

        final List<Conta> contasAVencer = this.service.listarContasProximasAoVencimento(diasParaVencimento);

        if (contasAVencer.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contasAVencer);
    }

    @PutMapping
    public ResponseEntity atualizarConta(@Valid @RequestBody AtualizarContaAPagarDTO contaDTO) {

        final Conta conta = this.service.atualizarConta(contaDTO);

        return ResponseEntity.ok(conta);
    }

}
