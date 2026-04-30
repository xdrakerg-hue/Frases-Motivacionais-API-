package com.nindo.frases.exception;

public class FraseNotFoundException extends RuntimeException {

    public FraseNotFoundException(Long id) {
        super("Frase com id " + id + " nao encontrada.");
    }

    public FraseNotFoundException(String mensagem) {
        super(mensagem);
    }
}
