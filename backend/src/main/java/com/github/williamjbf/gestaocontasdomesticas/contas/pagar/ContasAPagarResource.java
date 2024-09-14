package com.github.williamjbf.gestaocontasdomesticas.contas.pagar;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.ContasAPagarService;
import com.github.williamjbf.gestaocontasdomesticas.notificacao.Notificacao;
import com.github.williamjbf.gestaocontasdomesticas.notificacao.Notificador;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@RestController
@RequestMapping("/api/contas/pagar")
public class ContasAPagarResource {

    private final ContasAPagarService service;
    private final Notificador notificador;

    @Autowired
    public ContasAPagarResource(final ContasAPagarService service,
                                final Notificador notificador) {
        this.service = service;
        this.notificador = notificador;
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

    @GetMapping(value = "/proximas/vencimento", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Notificacao> listarContasProximasAoVencimento() {

        final Long mockUser = 1L;
        final Sinks.Many<Notificacao> notificacoes = Sinks.many().unicast().onBackpressureBuffer();

        notificador.inscrever(mockUser, notificacoes);

        return notificacoes.asFlux().log();
    }

    @PutMapping
    public ResponseEntity atualizarConta(@Valid @RequestBody AtualizarContaAPagarDTO contaDTO) {

        final Conta conta = this.service.atualizarConta(contaDTO);

        return ResponseEntity.ok(conta);
    }

}
