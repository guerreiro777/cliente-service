package br.com.bb.sorteio.cliente.domain.request;

import br.com.bb.sorteio.cliente.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

    @NotNull(message = "Nome deve ser informado", groups = OnCreate.class)
    @Size(min = 3, max = 300, message = "Nome deve ter no mínimo 3 e no máximo 300 caracteres")
    private String nmCliente;

    @NotNull(message = "CPF deve ser informado", groups = OnCreate.class)
    @Size(max = 11)
    private String nroCpf;

    @NotNull(message = "DDD deve ser informado", groups = OnCreate.class)
    @Min(2)
    private Integer nroDdd;

    @NotNull(message = "Telefone deve ser informado", groups = OnCreate.class)
    @Min(9)
    private Integer nroTelefone;

    @NotNull(message = "E-mail deve ser informado", groups = OnCreate.class)
    @Email(message = "Informe um e-mail válido")
    @Size(max = 50)
    private String strEmail;

    @NotNull(message = "Cupom Fiscal deve ser informado", groups = OnCreate.class)
    @Min(10)
    private Integer nroCupomFiscal;

    @JsonIgnore
    @Max(1)
    private Integer stEnvioSms;

    @JsonIgnore
    @Max(1)
    private Integer stCliente;
}
