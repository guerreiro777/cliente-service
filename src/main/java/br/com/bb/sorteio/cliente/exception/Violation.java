package br.com.bb.sorteio.cliente.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Violation {

    private final String fieldName;

    private final String message;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
