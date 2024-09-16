package com.github.williamjbf.gestaocontasdomesticas.security.filters;

import com.github.williamjbf.gestaocontasdomesticas.security.JwtUtil;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * O `SecurityFilter` é um filtro de segurança responsável por validar tokens JWT e autenticar usuários.
 *
 * Este filtro é executado uma vez por requisição e é responsável por extrair o token JWT do cabeçalho da requisição,
 * validar o token e autenticar o usuário baseado nas informações do token.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @Autowired
    public SecurityFilter(
            final JwtUtil jwtUtil,
            final UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {

        final String token = this.recoverToken(request);

        if (token != null) {
            final String login = jwtUtil.extractUsername(token);
            final UserDetails user = usuarioService.loadUserByUsername(login);

            if (user != null) {
                final UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");
        if (authorization == null) return null;
        return authorization.replace("Bearer ", "");
    }
}
