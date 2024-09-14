package com.github.williamjbf.gestaocontasdomesticas.cartoes.service;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.CriarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.repository.CartoesJparepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartoesService {

    private final CartoesJparepository repository;

    @Autowired
    public CartoesService(final CartoesJparepository repository) {
        this.repository = repository;
    }

    public void criarCartao(final CriarCartaoDTO cartaoDTO) {
        repository.save(cartaoDTO.toCartao());
    }

}
