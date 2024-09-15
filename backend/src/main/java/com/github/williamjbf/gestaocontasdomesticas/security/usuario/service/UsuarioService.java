package com.github.williamjbf.gestaocontasdomesticas.security.usuario.service;

import com.github.williamjbf.gestaocontasdomesticas.security.authentication.AuthenticationDTO;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.Usuario;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.repository.UsuarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UsuarioJpaRepository usuarioRepository;

    @Autowired
    public UsuarioService(final UsuarioJpaRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final Usuario usuario = usuarioRepository.findByLogin(login);

        return usuario;
    }

    public void cadastrar(final AuthenticationDTO authDTO) {
        final String senhaCriptografada = this.passwordEncoder.encode(authDTO.getPassword());
        authDTO.setPassword(senhaCriptografada);

        usuarioRepository.save(authDTO.toUsuario());
    }

    /**
     * Recupera o {@link Usuario} logado da requisição/thread usando o {@link SecurityContextHolder} do Spring
     *
     * @return Intancia de {@link Usuario} representando o Usuário logado
     * @throws RuntimeException Caso o Principal da requisição não seja um {@link Usuario}
     */
    public Usuario getUsuarioLogado() throws RuntimeException {

        final Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElse(null);

        if (authentication == null) {
            return null;
        }

        final Object principal = authentication.getPrincipal();

        try {
            final Usuario usuario = (Usuario) principal;
            return usuario;
        } catch (ClassCastException exception) {
            // todo: O ideal seria um exception dedicada para este problema
            throw new RuntimeException("O getPrincipal não é do tipo esperado");
        }
    }

}
