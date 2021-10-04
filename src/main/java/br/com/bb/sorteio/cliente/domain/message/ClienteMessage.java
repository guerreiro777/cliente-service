package br.com.bb.sorteio.cliente.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClienteMessage {

    private String nmCliente;
    private Integer nroDdd;
    private Integer nroTelefone;
    private String strEmail;
    private String nroCpf;
    private Integer nroCupomFiscal;

    public ClienteMessage(final String nmCliente, //
                          final Integer nroDdd, //
                          final Integer nroTelefone, //
                          final String strEmail, //
                          final String nroCpf, //
                          final Integer nroCupomFiscal
    )
    {
        this.nmCliente = nmCliente;
        this.nroDdd = nroDdd;
        this.nroTelefone = nroTelefone;
        this.strEmail = strEmail;
        this.nroCpf = nroCpf;
        this.nroCupomFiscal = nroCupomFiscal;
    }
}
