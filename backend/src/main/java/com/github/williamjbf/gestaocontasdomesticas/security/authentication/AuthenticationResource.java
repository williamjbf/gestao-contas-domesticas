package com.github.williamjbf.gestaocontasdomesticas.security.authentication;

import com.github.williamjbf.gestaocontasdomesticas.security.authentication.login.LoginService;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * O `AuthenticationResource` é um controlador REST responsável por gerenciar operações de autenticação e cadastro de usuários.
 *
 * Este controlador expõe endpoints para permitir que os usuários façam login e se cadastrem no sistema. Ele interage com
 * os serviços de autenticação e gerenciamento de usuários para realizar essas operações.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {

    private final UsuarioService usuarioService;
    private final LoginService loginService;

    @Autowired
    public AuthenticationResource(final UsuarioService usuarioService, final LoginService loginService) {
        this.usuarioService = usuarioService;
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){

        final LoginResponseDTO login = loginService.login(authenticationDTO);

        return ResponseEntity.ok(login);
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid AuthenticationDTO authenticationDTO){

        if(usuarioService.loadUserByUsername(authenticationDTO.getLogin()) != null) {
            return ResponseEntity.badRequest().build();
        }

        usuarioService.cadastrar(authenticationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
