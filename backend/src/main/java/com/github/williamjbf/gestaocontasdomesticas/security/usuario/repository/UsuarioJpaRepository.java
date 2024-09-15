package com.github.williamjbf.gestaocontasdomesticas.security.usuario.repository;

import com.github.williamjbf.gestaocontasdomesticas.security.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(final String login);

}
