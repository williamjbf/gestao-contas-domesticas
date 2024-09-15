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

    public void salvarConta(final ContaAReceberDTO contaAReceberDTO, final Long idUsuario) {
        final Conta conta = contaAReceberDTO.toConta();
        conta.setIdUsuario(idUsuario);

        repository.save(conta);
    }

    @Transactional(readOnly = true)
    public List<Conta> listarTodas(final Long idUsuario) {
        return repository.findAllByTipoContaAndIdUsuario(TipoConta.CONTA_A_RECEBER, idUsuario);
    }

    public Conta atualizarConta(final AtualizarContaAReceberDTO contaDTO, final Long idUsuario) {
        final Conta conta = contaDTO.toConta();
        conta.setIdUsuario(idUsuario);

        return repository.save(conta);
    }
}
