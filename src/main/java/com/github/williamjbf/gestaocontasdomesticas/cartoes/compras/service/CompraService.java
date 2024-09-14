package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.service;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto.CriarCompraDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.repository.CompraJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    private final CompraJpaRepository repository;

    @Autowired
    public CompraService(final CompraJpaRepository repository) {
        this.repository = repository;
    }

    public void adicionarCompra(final CriarCompraDTO compraDTO) {
        repository.save(compraDTO.toCompra());
    }
}
