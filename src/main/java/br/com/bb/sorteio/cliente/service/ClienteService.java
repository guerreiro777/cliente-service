package br.com.bb.sorteio.cliente.service;

import br.com.bb.sorteio.cliente.domain.request.ClienteRequest;
import br.com.bb.sorteio.cliente.entity.Cliente;
import br.com.bb.sorteio.cliente.exception.EnumBussinesException;
import br.com.bb.sorteio.cliente.repository.ClienteRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(final ClienteRequest clienteRequest) {
        Optional<Cliente> clienteOptional = clienteRepository.findByNroCpf(clienteRequest.getNroCpf());
        if (clienteOptional.isPresent()) {
            throw EnumBussinesException.CLIENTE_DUPLICADO.exception();
        }

        // Cadastra o cliente
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(clienteRequest, Cliente.class);
        cliente.setStCliente(1);
        cliente.setStEnvioSms(0);
        cliente.setStEnvioEmail(0);
        clienteRepository.save(cliente);

        // Retorna a entidade cliente
        return cliente;
    }

    @Transactional
    public Cliente update(final long id, final ClienteRequest clienteRequest) {
        Cliente cliente = this.findById(id);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setSkipNullEnabled(true);
        modelMapper.map(clienteRequest, cliente);

        return clienteRepository.save(cliente);
    }

    public Cliente findById(final Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            throw EnumBussinesException.NOT_FOUND.exception();
        }
        return cliente.get();
    }

    @Transactional
    public void delete(final Long id) {
        Cliente cliente = this.findById(id);
        clienteRepository.delete(cliente);
    }

    @Transactional
    public void changeStatusForEmailSentSuccessfully(final String nroCpf) {
        System.out.println("changeStatusForEmailSentSuccessfully");
        Optional<Cliente> clienteOptional = clienteRepository.findByNroCpf(nroCpf);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setStEnvioEmail(1);
            System.out.println(cliente);
            clienteRepository.save(cliente);
        }
    }

    @Transactional
    public void changeStatusForSmsSentSuccessfully(final String nroCpf) {
        System.out.println("changeStatusForSmsSentSuccessfully");
        Optional<Cliente> clienteOptional = clienteRepository.findByNroCpf(nroCpf);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setStEnvioSms(1);
            System.out.println(cliente);
            clienteRepository.save(cliente);
        }
    }

}
