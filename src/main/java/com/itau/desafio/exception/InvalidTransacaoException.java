package com.itau.desafio.exception;

public class InvalidTransacaoException extends RuntimeException {
    public InvalidTransacaoException(String message) {
        super(message);
    }
}
