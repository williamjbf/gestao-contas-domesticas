package com.github.williamjbf.gestaocontasdomesticas.contas.pagar.repository;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContasAPagarJpaRepository extends JpaRepository<Conta, Long> {

}
