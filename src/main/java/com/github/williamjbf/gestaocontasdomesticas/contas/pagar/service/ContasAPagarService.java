package com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.ContaAPagarDTO;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.repository.ContasAPagarJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContasAPagarService {

    private final ContasAPagarJpaRepository repository;

    @Autowired
    public ContasAPagarService(final  ContasAPagarJpaRepository repository) {
        this.repository = repository;
    }

    public void salvarConta(final ContaAPagarDTO contaAPagarDTO) {
        repository.save(contaAPagarDTO.toConta());
    }

    @Transactional(readOnly = true)
    public List<Conta> listarTodas() {
        return repository.findAllByTipoConta(TipoConta.CONTA_A_PAGAR);
    }

}
