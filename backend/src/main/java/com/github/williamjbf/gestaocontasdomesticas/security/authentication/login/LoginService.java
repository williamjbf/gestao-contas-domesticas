package com.github.williamjbf.gestaocontasdomesticas.security.authentication.login;

import com.github.williamjbf.gestaocontasdomesticas.security.JwtUtil;
import com.github.williamjbf.gestaocontasdomesticas.security.authentication.AuthenticationDTO;
import com.github.williamjbf.gestaocontasdomesticas.security.authentication.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(final JwtUtil jwtUtil,
                        final AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDTO login(final AuthenticationDTO authDTO) {
        final UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authDTO.getLogin(), authDTO.getPassword());

        final Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);
        final String token = jwtUtil.generateToken((UserDetails) authenticate.getPrincipal());

        return new LoginResponseDTO(token);
    }

}
