package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompraJpaRepository extends JpaRepository<Compra, Long> {

    List<Compra> findAllByIdUsuario(final Long idUsuario);

    List<Compra> findAllByCartao_IdAndIdUsuario(final Long IdCartao, final Long idUsuario);

    /**
     * Query que atualize a t_compra se uma das suas t_parcelas tenha id = idParcela
     */
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE t_compra compra SET compra.paga = ?2 WHERE id = ?1 and id_usuario = ?3")
    void updatePagaById(final Long IdCompra, final boolean paga, final Long idUsuario);

}
