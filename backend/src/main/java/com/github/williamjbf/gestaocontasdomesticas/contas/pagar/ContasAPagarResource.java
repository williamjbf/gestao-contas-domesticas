package com.github.williamjbf.gestaocontasdomesticas.contas.pagar;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.ContasAPagarService;
import com.github.williamjbf.gestaocontasdomesticas.notificacao.Notificacao;
import com.github.williamjbf.gestaocontasdomesticas.notificacao.Notificador;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.Usuario;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar as contas a pagar do sistema.
 *
 * Esta classe fornece endpoints que permitem aos usuários criar, atualizar, listar
 * e receber notificações sobre suas contas a pagar. As operações são protegidas, exigindo
 * que o usuário esteja autenticado para realizar as ações disponíveis.
 */
@RestController
@RequestMapping("/api/contas/pagar")
public class ContasAPagarResource {

    private final ContasAPagarService contaService;
    private final UsuarioService usuarioService;
    private final Notificador notificador;

    @Autowired
    public ContasAPagarResource(final ContasAPagarService contaService,
                                final UsuarioService usuarioService, 
                                final Notificador notificador) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
        this.notificador = notificador;
    }

    @PostMapping
    public ResponseEntity<Void> criarConta(@Valid @RequestBody final ContaAPagarDTO contaDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        this.contaService.salvarConta(contaDTO, idUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final List<Conta> contas = this.contaService.listarTodas(idUsuario);

        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contas);
    }

    @GetMapping(value = "/proximas/vencimento", produces = "text/event-stream")
    public Flux<Notificacao> listarContasProximasAoVencimento() {

        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final Sinks.Many<Notificacao> notificacoes = Sinks.many().unicast().onBackpressureBuffer();

        notificador.inscrever(idUsuario, notificacoes);

        return notificacoes.asFlux().log();
    }

    @PutMapping
    public ResponseEntity atualizarConta(@Valid @RequestBody AtualizarContaAPagarDTO contaDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final Conta conta = this.contaService.atualizarConta(contaDTO, idUsuario);

        return ResponseEntity.ok(conta);
    }

}
