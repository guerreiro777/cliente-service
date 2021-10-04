package br.com.bb.sorteio.cliente.component;

import br.com.bb.sorteio.cliente.service.AsyncMessageService;
import br.com.bb.sorteio.cliente.service.ClienteService;
import br.com.bb.sorteio.cliente.utils.JsonUtil;
import com.google.gson.JsonObject;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Log
@Component
public class EmailListenerController {

    private static final String QUEUE_NAME = "email-sent-successfully";

    @Autowired
    ClienteService clienteService;

    @Autowired
    AsyncMessageService asyncMessageService;

    @JmsListener(destination = QUEUE_NAME, containerFactory = "myFactory")
    public void listenEmailMessageSucessfullySent(final String clienteMessage) {
        log.log(Level.INFO, "Fila recebida: " + QUEUE_NAME + " " + clienteMessage);
        JsonObject cliente = JsonUtil.stringToJson(clienteMessage);
        final String nroCpf = cliente.get("nroCpf").getAsString();
        clienteService.changeStatusForEmailSentSuccessfully(nroCpf);
        asyncMessageService.sendMessageRegisteredCustomer(nroCpf);
    }
}
