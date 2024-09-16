package com.github.williamjbf.gestaocontasdomesticas.contas.repository;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.Status;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.dto.GastosMensais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContasJpaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findAllByTipoContaAndIdUsuario(final TipoConta tipoConta, final Long idUsuario);

    List<Conta> findAllByTipoContaAndStatusAndIdUsuario(final TipoConta tipoConta, final Status status, final Long idUsuario);

    List<Conta> findAllByTipoContaAndStatus(final TipoConta tipoConta, final Status status);

    @Query(nativeQuery = true,value = "SELECT mes_ano, SUM(valor_total), categoria FROM((SELECT TO_CHAR(data, 'MM/YYYY') AS mes_ano, SUM(valor) AS valor_total, categoria FROM t_conta WHERE tipo_conta = 'CONTA_A_PAGAR' AND id_usuario = 1 GROUP BY mes_ano, categoria) UNION ALL (SELECT TO_CHAR(data_compra, 'MM/YYYY') AS mes_ano, SUM(valor) AS valor_total, 'Cartao de Credito: ' || categoria AS categoria FROM t_compra WHERE id_usuario = 1 AND id NOT IN (SELECT id_compra FROM t_parcela) GROUP BY mes_ano, categoria) UNION ALL (SELECT TO_CHAR(parcela.data_cobranca, 'MM/YYYY') AS mes_ano, SUM(parcela.valor) AS valor_total, 'Cartao de Credito: ' || compra.categoria AS categoria FROM t_parcela parcela JOIN t_compra compra ON parcela.id_compra = compra.id WHERE compra.id_usuario = 1 GROUP BY mes_ano, compra.categoria)) AS data GROUP BY mes_ano, categoria ORDER BY TO_DATE(mes_ano, 'MM/YYYY') DESC, categoria ASC;")
    List<Object[]> recuperarTotalGastoPorMes(Long idUsuario);
}
