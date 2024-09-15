package com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.Status;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.AtualizarContaAPagarDTO;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.ContaAPagarDTO;
import com.github.williamjbf.gestaocontasdomesticas.contas.repository.ContasJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContasAPagarService {

    private final ContasJpaRepository repository;

    @Autowired
    public ContasAPagarService(final ContasJpaRepository repository) {
        this.repository = repository;
    }

    public void salvarConta(final ContaAPagarDTO contaAPagarDTO, final Long idUsuario) {
        final Conta conta = contaAPagarDTO.toConta();
        conta.setIdUsuario(idUsuario);

        repository.save(conta);
    }

    @Transactional(readOnly = true)
    public List<Conta> listarTodas(final Long idUsuario) {
        return repository.findAllByTipoContaAndIdUsuario(TipoConta.CONTA_A_PAGAR, idUsuario);
    }

    public List<Conta> listarContasProximasAoVencimento(final Long diasParaVencimento, final Long idUsuario) {
        final List<Conta> contasPendentes = repository
                .findAllByTipoContaAndStatusAndIdUsuario(TipoConta.CONTA_A_PAGAR, Status.PENDENTE, idUsuario);

        final LocalDate dataLimiteVencimento = LocalDate.now().plusDays(diasParaVencimento);

        return contasPendentes.stream()
                .filter(conta -> !conta.getData().isAfter(dataLimiteVencimento))
                .collect(Collectors.toList());
    }

    public Map<Long, List<Conta>> recuperarContasProximasAoVencimento(final Long diasParaVencimento) {

        final List<Conta> contasPendentes = repository
                .findAllByTipoContaAndStatus(TipoConta.CONTA_A_PAGAR, Status.PENDENTE);

        final LocalDate dataLimiteVencimento = LocalDate.now().plusDays(diasParaVencimento);

        return contasPendentes.stream()
                .filter(conta -> !conta.getData().isAfter(dataLimiteVencimento))
                .collect(Collectors.groupingBy(Conta::getIdUsuario));
    }

    public Conta atualizarConta(final AtualizarContaAPagarDTO contaDTO, final Long idUsuario) {
        final Conta conta = contaDTO.toConta();
        conta.setIdUsuario(idUsuario);

        return repository.save(conta);
    }
}
