package com.github.williamjbf.gestaocontasdomesticas.security.authentication;

import com.github.williamjbf.gestaocontasdomesticas.security.usuario.Usuario;
import lombok.Data;

@Data
public class AuthenticationDTO {

    private String login;
    private String password;

    public Usuario toUsuario(){
        final Usuario usuario = new Usuario();

        usuario.setLogin(this.login);
        usuario.setPassword(this.password);

        return usuario;
    }
}
