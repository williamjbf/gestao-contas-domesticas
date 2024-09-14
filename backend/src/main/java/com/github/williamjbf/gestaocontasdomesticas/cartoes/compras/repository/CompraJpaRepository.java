package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraJpaRepository extends JpaRepository<Compra, Long> {

    List<Compra> findAllByCartao_Id(final Long IdCartao);
}
