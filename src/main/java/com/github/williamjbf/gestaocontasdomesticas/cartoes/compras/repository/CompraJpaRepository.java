package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraJpaRepository extends JpaRepository<Compra, Long> {
}
