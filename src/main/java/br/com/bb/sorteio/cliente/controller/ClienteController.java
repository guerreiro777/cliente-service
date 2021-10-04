package br.com.bb.sorteio.cliente.controller;

import br.com.bb.sorteio.cliente.domain.request.ClienteRequest;
import br.com.bb.sorteio.cliente.entity.Cliente;
import br.com.bb.sorteio.cliente.service.AsyncMessageService;
import br.com.bb.sorteio.cliente.service.ClienteService;
import br.com.bb.sorteio.cliente.validation.OnCreate;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/v1/cliente")
@Log
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    AsyncMessageService asyncMessageService;

    @ApiOperation(value = "Retorna um cliente", response = Cliente.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable @NotNull long id) {
        return clienteService.findById(id);
    }

    @ApiOperation(value = "Cria um cliente", response = Cliente.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    public Cliente createCliente(@Validated(value = OnCreate.class) @RequestBody ClienteRequest clienteRequest) {
        // Salva o cliente
        Cliente cliente = clienteService.save(clienteRequest);

        // Cadastra fila para envio de e-mail e SMS ao cliente
        asyncMessageService.sendSmsMessage(cliente);
        asyncMessageService.sendEmailMessage(cliente);

        return cliente;
    }

    @ApiOperation(value = "Atualiza um cliente", response = Cliente.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable @NotNull long id, @Valid @RequestBody ClienteRequest clienteRequest) {
        return clienteService.update(id, clienteRequest);
    }

    @ApiOperation(value = "Deleta um cliente", response = ResponseEntity.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCliente(@PathVariable @NotNull long id) {
        clienteService.delete(id);
        return ResponseEntity.ok().build();
    }

}
