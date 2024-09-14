package com.github.williamjbf.gestaocontasdomesticas.cartoes.repository;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartoesJparepository extends JpaRepository<Cartao,Long> {
}
