package com.github.williamjbf.gestaocontasdomesticas.contas.receber.service;

import com.github.williamjbf.gestaocontasdomesticas.contas.Conta;
import com.github.williamjbf.gestaocontasdomesticas.contas.TipoConta;
import com.github.williamjbf.gestaocontasdomesticas.contas.receber.AtualizarContaAReceberDTO;
import com.github.williamjbf.gestaocontasdomesticas.contas.receber.ContaAReceberDTO;
import com.github.williamjbf.gestaocontasdomesticas.contas.repository.ContasJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContasAReceberService {

    private final ContasJpaRepository repository;

    @Autowired
    public ContasAReceberService(final ContasJpaRepository repository) {
        this.repository = repository;
    }

    public void salvarConta(final ContaAReceberDTO contaAReceberDTO) {
        repository.save(contaAReceberDTO.toConta());
    }

    @Transactional(readOnly = true)
    public List<Conta> listarTodas() {
        return repository.findAllByTipoConta(TipoConta.CONTA_A_RECEBER);
    }

    public Conta atualizarConta(final AtualizarContaAReceberDTO contaDTO) {
        return repository.save(contaDTO.toConta());
    }
}
