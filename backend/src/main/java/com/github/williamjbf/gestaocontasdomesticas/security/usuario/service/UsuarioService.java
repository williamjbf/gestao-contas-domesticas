package com.github.williamjbf.gestaocontasdomesticas.security.usuario.service;

import com.github.williamjbf.gestaocontasdomesticas.security.JwtUtil;
import com.github.williamjbf.gestaocontasdomesticas.security.authentication.AuthenticationDTO;
import com.github.williamjbf.gestaocontasdomesticas.security.authentication.LoginResponseDTO;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.Usuario;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.repository.UsuarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {


    private final UsuarioJpaRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UsuarioService(final UsuarioJpaRepository usuarioRepository,
                          final JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final Usuario usuario = usuarioRepository.findByLogin(login);

        return usuario;
    }

    public LoginResponseDTO login(final AuthenticationDTO authDTO) {
        final UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authDTO.getLogin(), authDTO.getPassword());
        final Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);
        final String token = jwtUtil.generateToken((UserDetails) authenticate.getPrincipal());
        return new LoginResponseDTO(token);
    }

    public void cadastrar(final AuthenticationDTO authDTO){

        final String senhaCriptografada = new BCryptPasswordEncoder().encode(authDTO.getPassword());
        authDTO.setPassword(senhaCriptografada);

        usuarioRepository.save(authDTO.toUsuario());
    }
}
