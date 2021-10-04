package br.com.bb.sorteio.cliente.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BusinessException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    public BusinessException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
