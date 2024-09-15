package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.repository;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelaJpaRepository extends JpaRepository<Parcela, Long>  {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM t_parcela WHERE id_compra = ?1")
    void deleteByCompraId(final Long idCompra);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE t_parcela SET paga=?2 WHERE id = ?1 RETURNING id_compra")
    Long updatePagaByIdReturningIdCompra(final Long idParcela, final boolean paga);

    @Query(nativeQuery = true, value = "SELECT * FROM t_parcela WHERE id_compra = ?1")
    List<Parcela> recuperaTodasParcelasByIdCompra(final Long idCompra);

}
