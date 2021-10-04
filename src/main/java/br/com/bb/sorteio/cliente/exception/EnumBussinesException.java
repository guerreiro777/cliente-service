package br.com.bb.sorteio.cliente.exception;

import org.springframework.http.HttpStatus;

public enum EnumBussinesException {

    NOT_FOUND("Cliente não encontrado", HttpStatus.NOT_FOUND),
    CLIENTE_DUPLICADO("O CPF informado já existe na base de dados", HttpStatus.CONFLICT)
    ;

    final String message;
    final HttpStatus httpStatus;

    EnumBussinesException(final String message, final HttpStatus httpStatus)
    {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BusinessException exception()
    {
        return new BusinessException(this.message, this.httpStatus);
    }
}
