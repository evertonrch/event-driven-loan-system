package com.lab.ms_proposta.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Bean
    public Queue propostaPendenteAnaliseCredito() {
        final String QUEUE = "proposta-pendente.ms-analise-credito";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Queue propostaPendenteNotificacao() {
        final String QUEUE = "proposta-pendente.ms-notificacao";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Queue propostaConcluidaProposta() {
        final String QUEUE = "proposta-concluida.ms-proposta";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Queue propostaConcluidaNotificacao() {
        final String QUEUE = "proposta-concluida.ms-notificacao";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criaFanoutExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange("proposta-pendente.ex").build();
    }

    @Bean
    public Binding bindingPropostaPendenteMsAnaliseCredito() {
        return BindingBuilder
                .bind(propostaPendenteAnaliseCredito())
                .to(criaFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding bindingPropostaPendenteMsNotificacao() {
        return BindingBuilder
                .bind(propostaPendenteNotificacao())
                .to(criaFanoutExchangePropostaPendente());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate restTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setBeanName("restTemplate");
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }
}
