package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelaJpaRepository extends JpaRepository<Parcela, Long>  {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM t_parcela WHERE id_compra = ?1")
    void deleteByCompraId(final Long idCompra);

}
