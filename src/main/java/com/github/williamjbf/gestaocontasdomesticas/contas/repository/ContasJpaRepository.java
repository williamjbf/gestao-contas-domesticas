package com.github.williamjbf.gestaocontasdomesticas.contas.repository;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContasJpaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findAllByTipoConta(final TipoConta tipoConta);

}
