package com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service;

import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.ContaAPagarDTO;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.repository.ContasAPagarJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContasAPagarService {

    @Autowired
    private ContasAPagarJpaRepository repository;

    public void salvarConta(final ContaAPagarDTO contaAPagarDTO) {

        repository.save(contaAPagarDTO.toConta());
    }

}
