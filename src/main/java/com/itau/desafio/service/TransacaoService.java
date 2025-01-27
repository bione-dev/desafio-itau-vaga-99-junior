package com.itau.desafio.service;

import com.itau.desafio.dto.EstatisticaDTO;
import com.itau.desafio.dto.TransacaoDTO;
import com.itau.desafio.exception.InvalidTransacaoException;
import com.itau.desafio.model.Transacao;
import com.itau.desafio.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    /**
     * Adiciona uma nova transação após validar os dados.
     *
     * @param transacaoDTO DTO contendo os dados da transação.
     */
    public void criarTransacao(TransacaoDTO transacaoDTO) {
        validarTransacao(transacaoDTO);
        Transacao transacao = new Transacao(transacaoDTO.getValor(), transacaoDTO.getDataHora());
        repository.salvar(transacao);
    }

    /**
     * Limpa todas as transações armazenadas.
     */
    public void limparTransacoes() {
        repository.limpar();
    }

    /**
     * Calcula as estatísticas das transações realizadas nos últimos 60 segundos.
     *
     * @return EstatisticaDTO contendo os dados das estatísticas (count, sum, avg, min, max).
     */
    public EstatisticaDTO calcularEstatisticas() {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime umMinutoAtras = agora.minusSeconds(60);

        // Filtrar transações nos últimos 60 segundos
        List<Transacao> transacoesRecentes = repository.listar().stream()
                .filter(transacao -> transacao.getDataHora().isAfter(umMinutoAtras))
                .collect(Collectors.toList());

        // Verificar se não há transações
        if (transacoesRecentes.isEmpty()) {
            return new EstatisticaDTO(0, 0, 0, 0, 0); // Todos os valores zerados
        }

        // Calcular estatísticas
        DoubleSummaryStatistics stats = transacoesRecentes.stream()
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        // Retornar estatísticas calculadas
        return new EstatisticaDTO(
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getMin(),
                stats.getMax()
        );
    }

    /**
     * Valida os dados de uma transação.
     *
     * @param transacaoDTO DTO contendo os dados da transação.
     */
    private void validarTransacao(TransacaoDTO transacaoDTO) {
        if (transacaoDTO.getValor() < 0) {
            throw new InvalidTransacaoException("O valor da transação não pode ser negativo.");
        }

        if (transacaoDTO.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new InvalidTransacaoException("A data da transação não pode estar no futuro.");
        }
    }
}
