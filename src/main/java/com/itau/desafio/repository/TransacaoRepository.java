package com.itau.desafio.repository;

import com.itau.desafio.model.Transacao;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransacaoRepository {
    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public void salvar(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> listar() {
        return Collections.unmodifiableList(transacoes);
    }

    public void limpar() {
        transacoes.clear();
    }
}
