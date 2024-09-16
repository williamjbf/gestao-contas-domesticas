package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.service;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.Parcela;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.dto.in.StatusPagamento;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.repository.ParcelaJpaRepository;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository.CompraJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParcelaService {

    private final ParcelaJpaRepository repository;
    private final CompraJpaRepository compraRepository;

    @Autowired
    public ParcelaService(final ParcelaJpaRepository repository,
                          final CompraJpaRepository compraRepository) {
        this.repository = repository;
        this.compraRepository = compraRepository;
    }

    @Transactional
    public void definirPagamento(final Long idParcela, final StatusPagamento statusPagamento, final Long idUsuario) {

        if (statusPagamento.isPago()) {
            this.processParcelaAsPago(idParcela, idUsuario);
        } else {
            this.processParcelaAsNaoPaga(idParcela, idUsuario);
        }
    }

    private void processParcelaAsPago(final Long idParcela, final Long idUsuario) {
        this.repository.updatePagaById(idParcela, true, idUsuario);

        // se todas as parcelas estiverem pagas, então a Compra foi totalmente paga
        final Long idCompra = this.repository.findIdCompraById(idParcela, idUsuario);
        final List<Parcela> parcelasDaCompra = this.repository.recuperaTodasParcelasByIdCompra(idCompra, idUsuario);

        boolean todasPagas = parcelasDaCompra.stream()
                .allMatch(Parcela::isPaga);

        if (todasPagas) {
            this.compraRepository.updatePagaById(idCompra, true, idUsuario);
        }
    }

    private void processParcelaAsNaoPaga(final Long idParcela, final Long idUsuario) {
        this.repository.updatePagaById(idParcela, false, idUsuario);

        // Se uma das parcelas não estiver paga então a Compra ainda não foi totalmente paga
        final Long idCompra = this.repository.findIdCompraById(idParcela, idUsuario);
        this.compraRepository.updatePagaById(idCompra, false, idUsuario);
    }

}
