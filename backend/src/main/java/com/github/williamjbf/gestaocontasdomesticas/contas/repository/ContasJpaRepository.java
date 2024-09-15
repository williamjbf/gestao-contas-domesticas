package com.github.williamjbf.gestaocontasdomesticas.contas.repository;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.Status;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContasJpaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findAllByTipoContaAndIdUsuario(final TipoConta tipoConta, final Long idUsuario);

    List<Conta> findAllByTipoContaAndStatusAndIdUsuario(final TipoConta tipoConta, final Status status, final Long idUsuario);

    List<Conta> findAllByTipoContaAndStatus(final TipoConta tipoConta, final Status status);
}
