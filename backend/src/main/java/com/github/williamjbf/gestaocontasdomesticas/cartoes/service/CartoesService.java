package com.github.williamjbf.gestaocontasdomesticas.cartoes.service;

import com.github.williamjbf.gestaocontasdomesticas.cartoes.Cartao;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.AtualizarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.dto.CriarCartaoDTO;
import com.github.williamjbf.gestaocontasdomesticas.cartoes.repository.CartoesJparepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartoesService {

    private final CartoesJparepository repository;

    @Autowired
    public CartoesService(final CartoesJparepository repository) {
        this.repository = repository;
    }

    public void criarCartao(final CriarCartaoDTO cartaoDTO, final Long idUsuario) {
        final Cartao cartao = cartaoDTO.toCartao();
        cartao.setIdUsuario(idUsuario);

        repository.save(cartao);
    }

    public List<Cartao> listarCartoes(final Long idUsuario) {
        final List<Cartao> cartoes = repository.findAllByIdUsuario(idUsuario);
        return cartoes;
    }

    public Cartao editarCartao(final AtualizarCartaoDTO cartaoDTO, final Long idUsuario) {
        final Cartao cartao = cartaoDTO.toCartao();
        cartao.setIdUsuario(idUsuario);

        return repository.save(cartao);
    }
}
