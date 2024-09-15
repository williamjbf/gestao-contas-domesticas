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
    @Query(nativeQuery = true, value = "DELETE FROM t_parcela WHERE id_compra = ?1 and id_usuario = ?2")
    void deleteByCompraId(final Long idCompra, final Long idUsuario);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE t_parcela SET paga=?2 WHERE id = ?1 and id_usuario = ?3")
    void updatePagaById(final Long idParcela, final boolean paga, final Long idUsuario);

    @Query(nativeQuery = true, value = "SELECT id_compra FROM t_parcela WHERE id = ?1 and id_usuario = ?2")
    Long findIdCompraById(final Long idParcela, final Long idUsuario);

    @Query(nativeQuery = true, value = "SELECT * FROM t_parcela WHERE id_compra = ?1 and id_usuario = ?2")
    List<Parcela> recuperaTodasParcelasByIdCompra(final Long idCompra, final Long idUsuario);

}
