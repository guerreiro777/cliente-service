package br.com.bb.sorteio.cliente.configuration;

import br.com.bb.sorteio.cliente.domain.message.ClienteMessage;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
@Configuration
public class JmsConfig {

    @Bean
    public JmsListenerContainerFactory<?> myFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        // anonymous class
        factory.setErrorHandler(
                new ErrorHandler() {
                    @Override
                    public void handleError(Throwable t) {
                        log.log(Level.WARNING, "An error has occurred in the transaction", t);
                    }
                });

        // lambda function
        factory.setErrorHandler(t -> log.log(Level.WARNING, "An error has occurred in the transaction", t));

        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
