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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContasAPagarService {

    private final ContasJpaRepository repository;

    @Autowired
    public ContasAPagarService(final ContasJpaRepository repository) {
        this.repository = repository;
    }

    public void salvarConta(final ContaAPagarDTO contaAPagarDTO) {
        repository.save(contaAPagarDTO.toConta());
    }

    @Transactional(readOnly = true)
    public List<Conta> listarTodas() {
        return repository.findAllByTipoConta(TipoConta.CONTA_A_PAGAR);
    }

    public List<Conta> listarContasProximasAoVencimento(final Long diasParaVencimento) {
        final List<Conta> contasPendentes = repository.findAllByTipoContaAndStatus(TipoConta.CONTA_A_PAGAR, Status.PENDENTE);

        final LocalDate dataLimiteVencimento = LocalDate.now().plusDays(diasParaVencimento);

        return contasPendentes.stream()
                .filter(conta -> !conta.getData().isAfter(dataLimiteVencimento))
                .collect(Collectors.toList());
    }

    public Conta atualizarConta(final AtualizarContaAPagarDTO contaDTO) {
        return repository.save(contaDTO.toConta());
    }
}
