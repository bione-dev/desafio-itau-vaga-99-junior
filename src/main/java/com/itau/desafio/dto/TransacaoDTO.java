package com.itau.desafio.dto;

import java.time.OffsetDateTime;

public class TransacaoDTO {
    private double valor;
    private OffsetDateTime dataHora;

    // Getters e Setters
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
