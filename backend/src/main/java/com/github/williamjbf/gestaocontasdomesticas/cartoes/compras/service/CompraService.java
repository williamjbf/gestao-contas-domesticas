package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.service;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.Compra;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.Parcela;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.CriarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.EditarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository.CompraJpaRepository;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.parcela.repository.ParcelaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

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
    public void adicionarCompra(final CriarCompraDTO compraDTO) {
        final Compra compraToPersist = compraDTO.toCompra();

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
                    i
            );
            parcelas.add(parcela);
        }

        compraToGenerate.setParcelas(parcelas);
    }

    public List<Compra> listarCompras() {
        final List<Compra> compras = repository.findAll();

        return compras;
    }

    @Transactional
    public Compra editarCompra(final EditarCompraDTO compraDTO) {
        final Compra compraToUpdate = compraDTO.toCompra();

        /**
         * para uma implementação mais simples e rápida apenas estou removendo todas as parcelas
         * e as re-criando se necessário. Em uma implementação mais robusta e otimizada eu
         * poderia re-utilizar as parcelas já criadas e apenas refletir as alterações
         */
        parcelaRepository.deleteByCompraId(compraToUpdate.getId());

        if (compraDTO.isParcelada()) {
            this.generateParcelas(compraToUpdate, compraDTO.getQuantidadeParcelas());
        }

        return repository.save(compraToUpdate);
    }

    public List<Compra> buscarComprasPorCartao(final Long idCartao) {
        final List<Compra> compras = repository.findAllByCartao_Id(idCartao);

        return compras;
    }

}
