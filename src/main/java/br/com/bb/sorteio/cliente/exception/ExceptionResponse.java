package br.com.bb.sorteio.cliente.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private Date timestamp;
    private String message;

}
