package com.github.williamjbf.gestaocontasdomesticas.contas.receber;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.receber.service.ContasAReceberService;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar as contas a pagar do sistema.
 *
 * Esta classe fornece endpoints que permitem aos usuários criar, atualizar, listar
 * e receber notificações sobre suas contas a receber. As operações são protegidas, exigindo
 * que o usuário esteja autenticado para realizar as ações disponíveis.
 */
@RestController
@RequestMapping("/api/contas/receber")
public class ContasAReceberResource {

    private final ContasAReceberService contaService;
    private final UsuarioService usuarioService;

    @Autowired
    public ContasAReceberResource(final ContasAReceberService contaService,
                                  final UsuarioService usuarioService) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> criarConta(@Valid @RequestBody final ContaAReceberDTO contaDTO) {
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

    @PutMapping
    public ResponseEntity atualizarConta(@Valid @RequestBody AtualizarContaAReceberDTO contaDTO) {
        final Long idUsuario = usuarioService.getUsuarioLogado().getId();

        final Conta conta = this.contaService.atualizarConta(contaDTO, idUsuario);

        return ResponseEntity.ok(conta);
    }

}
