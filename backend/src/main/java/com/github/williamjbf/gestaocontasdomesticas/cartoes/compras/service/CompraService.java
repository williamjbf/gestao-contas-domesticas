package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.service;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Compra;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.CriarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.EditarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.StatusPagamento;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.Parcela;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.repository.ParcelaJpaRepository;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository.CompraJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    private final CompraJpaRepository repository;
    private final ParcelaJpaRepository parcelaRepository;

    @Autowired
    public CompraService(final CompraJpaRepository repository,
                         final ParcelaJpaRepository parcelaRepository) {
        this.repository = repository;
        this.parcelaRepository = parcelaRepository;
    }

    @Transactional
    public void adicionarCompra(final CriarCompraDTO compraDTO, final Long idUsuario) {
        final Compra compraToPersist = compraDTO.toCompra();
        compraToPersist.setIdUsuario(idUsuario);

        if (compraDTO.isParcelada()) {
            this.generateParcelas(compraToPersist, compraDTO.getQuantidadeParcelas());
        }

        repository.save(compraToPersist);
    }

    /**
     * Gera as parcelas de uma Compra. O valores quebrados (centavos) são centralizados na primeira parcela, nas
     * demais parcelas o valor sempre será inteiro, exemplo:
     * ValorTotal: 450,01 :: Quantidade de Parcelas: 10
     *  - 1 parcela: 45,01
     *  - demais parcelas: 45,00
     * </br>
     *  ValorTotal: 1234,56 :: Quantidade de Parcelas: 7
     *  - 1 parcela: 178.56
     *  - demais parcelas: 176.00
     */
    private void generateParcelas(final Compra compraToGenerate, final Integer quantidadeParcelas) {
        if (quantidadeParcelas == null || quantidadeParcelas <= 1) {
            return;
        }

        if (compraToGenerate == null) {
            return;
        }

        final LocalDate dataCompra = compraToGenerate.getDataCompra();
        // Garante que o valor total tenha 2 casas decimais
        BigDecimal valorTotal = compraToGenerate.getValor().setScale(2, RoundingMode.HALF_EVEN);

        // Divide o valor total pelo número de parcelas, arredondando para baixo
        BigDecimal valorParcelaInteira = valorTotal.divide(BigDecimal.valueOf(quantidadeParcelas), 0, RoundingMode.DOWN);

        // Calcula o valor do restante (centavos) que foi arredondado
        BigDecimal valorRestante = valorTotal.subtract(valorParcelaInteira.multiply(BigDecimal.valueOf(quantidadeParcelas)));

        final List<Parcela> parcelas = new ArrayList<>();
        for (long i = 1; i <= quantidadeParcelas; i++) {
            // A primeira parcela recebe o valor inteiro + o valor quebrado (centavos)
            // As demais parcelas recebem o valor inteiro
            final BigDecimal valorAtual = (i == 1)
                    ? valorParcelaInteira.add(valorRestante).setScale(2, RoundingMode.HALF_EVEN)
                    : valorParcelaInteira.setScale(2, RoundingMode.HALF_EVEN);

            final Parcela parcela = Parcela.of(
                    valorAtual,
                    dataCompra.plusMonths(i - 1),
                    i,
                    compraToGenerate.getIdUsuario()
            );
            parcelas.add(parcela);
        }

        compraToGenerate.setParcelas(parcelas);
    }

    @Transactional
    public List<Compra> listarCompras(final Long idUsuario) {
        final List<Compra> compras = repository.findAllByIdUsuarioOrderById(idUsuario);

        return compras;
    }

    @Transactional
    public Compra editarCompra(final EditarCompraDTO compraDTO, final Long idUsuario) {
        final Compra compraToUpdate = compraDTO.toCompra();
        compraToUpdate.setIdUsuario(idUsuario);

        /**
         * para uma implementação mais simples e rápida apenas estou removendo todas as parcelas
         * e as re-criando se necessário. Em uma implementação mais robusta e otimizada eu
         * poderia re-utilizar as parcelas já criadas e apenas refletir as alterações
         */
        parcelaRepository.deleteByCompraId(compraToUpdate.getId(), compraToUpdate.getIdUsuario());

        if (compraDTO.isParcelada()) {
            this.generateParcelas(compraToUpdate, compraDTO.getQuantidadeParcelas());
        }

        return repository.save(compraToUpdate);
    }

    @Transactional
    public List<Compra> buscarComprasPorCartao(final Long idCartao, final Long idUsuario) {
        final List<Compra> compras = repository.findAllByCartao_IdAndIdUsuarioOrderById(idCartao, idUsuario);

        return compras;
    }

    @Transactional
    public void definirPagamento(final Long idCompra, final StatusPagamento statusPagamento, final Long idUsuario) {

        if (statusPagamento.isPago()) {
            this.processCompraAsPago(idCompra, idUsuario);
        } else {
            this.processCompraAsNaoPaga(idCompra, idUsuario);
        }
    }

    private void processCompraAsPago(final Long idCompra, final Long idUsuario) {
        this.repository.updatePagaById(idCompra, true, idUsuario);

        // se Compra está paga todas as suas Parcelas devem estar pagas
        this.parcelaRepository.updatePagaByIdCompra(idCompra, true, idUsuario);
    }

    private void processCompraAsNaoPaga(final Long idCompra, final Long idUsuario) {
        this.repository.updatePagaById(idCompra, false, idUsuario);

        // Se uma Compra for marcada como não paga, todas as suas Parcelas são marcadas como não paga
        this.parcelaRepository.updatePagaByIdCompra(idCompra, false, idUsuario);
    }

}
