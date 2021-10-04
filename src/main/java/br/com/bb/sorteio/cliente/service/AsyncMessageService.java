package br.com.bb.sorteio.cliente.service;

import br.com.bb.sorteio.cliente.domain.message.ClienteMessage;
import br.com.bb.sorteio.cliente.entity.Cliente;
import br.com.bb.sorteio.cliente.repository.ClienteRepository;
import br.com.bb.sorteio.cliente.utils.JsonUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class AsyncMessageService {

    private static final String SMS_QUEUE_NAME = "send-sms-registered-customer";
    private static final String EMAIL_QUEUE_NAME = "send-email-registered-customer";
    private static final String REGISTERED_CUSTOMER_QUEUE_NAME = "registered-customer";

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ClienteRepository clienteRepository;

    private void createQueueInJsonFormat(final String queueName, final Object objectMessage) {
        String jsonMessage = JsonUtil.objectToJson(objectMessage);
        jmsTemplate.convertAndSend(queueName, jsonMessage);
        log.log(Level.INFO, "Fila cadastrada: " + queueName + " " + jsonMessage);
    }

    public void sendSmsMessage(final Cliente cliente) {
        ClienteMessage clienteMessage = new ClienteMessage();
        clienteMessage.setNmCliente(cliente.getNmCliente());
        clienteMessage.setNroDdd(cliente.getNroDdd());
        clienteMessage.setNroTelefone(cliente.getNroTelefone());
        clienteMessage.setNroCpf(cliente.getNroCpf());
        this.createQueueInJsonFormat(SMS_QUEUE_NAME, clienteMessage);
    }

    public void sendEmailMessage(final Cliente cliente) {
        ClienteMessage clienteMessage = new ClienteMessage();
        clienteMessage.setNmCliente(cliente.getNmCliente());
        clienteMessage.setStrEmail(cliente.getStrEmail());
        clienteMessage.setNroCpf(cliente.getNroCpf());
        this.createQueueInJsonFormat(EMAIL_QUEUE_NAME, clienteMessage);
    }

    public void sendMessageRegisteredCustomer(final String nroCpf) {
        Optional<Cliente> optionalCliente = clienteRepository.findByNroCpf(nroCpf);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            ClienteMessage customerRegistered = new ClienteMessage();
            customerRegistered.setNmCliente(cliente.getNmCliente());
            customerRegistered.setNroCpf(cliente.getNroCpf());
            customerRegistered.setStrEmail(cliente.getStrEmail());
            customerRegistered.setNroCupomFiscal(cliente.getNroCupomFiscal());
            customerRegistered.setNroDdd(cliente.getNroDdd());
            customerRegistered.setNroTelefone(cliente.getNroTelefone());
            this.createQueueInJsonFormat(REGISTERED_CUSTOMER_QUEUE_NAME, customerRegistered);
        }
    }
}
