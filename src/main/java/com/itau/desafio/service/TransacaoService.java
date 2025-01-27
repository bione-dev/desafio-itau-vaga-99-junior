package com.itau.desafio.service;

import com.itau.desafio.dto.TransacaoDTO;
import com.itau.desafio.exception.InvalidTransacaoException;
import com.itau.desafio.model.Transacao;
import com.itau.desafio.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public void criarTransacao(TransacaoDTO transacaoDTO) {
        validarTransacao(transacaoDTO);
        Transacao transacao = new Transacao(transacaoDTO.getValor(), transacaoDTO.getDataHora());
        repository.salvar(transacao);
    }

    public void limparTransacoes() {
        repository.limpar();
    }

    private void validarTransacao(TransacaoDTO transacaoDTO) {
        if (transacaoDTO.getValor() < 0) {
            throw new InvalidTransacaoException("O valor da transação não pode ser negativo.");
        }

        if (transacaoDTO.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new InvalidTransacaoException("A data da transação não pode estar no futuro.");
        }
    }
}
