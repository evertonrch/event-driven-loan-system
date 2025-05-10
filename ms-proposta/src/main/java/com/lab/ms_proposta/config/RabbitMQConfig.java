package com.lab.ms_proposta.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.proposta.pendente.exchange}")
    private String exchangePropostaPendente;

    @Value("${rabbitmq.proposta.concluida.exchange}")
    private String exchangePropostaConcluida;


    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        final String QUEUE = "proposta-pendente.ms-analise-credito";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao() {
        final String QUEUE = "proposta-pendente.ms-notificacao";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        final String QUEUE = "proposta-concluida.ms-proposta";
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
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
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendente).build();
    }

    @Bean
    public FanoutExchange criaFanoutExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluida).build();
    }

    @Bean
    public Binding bindingPropostaPendenteMsAnaliseCredito() {
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criaFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding bindingPropostaPendenteMsNotificacao() {
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criaFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding bindingPropostaConcluidaMsProposta() {
        return BindingBuilder
                .bind(criarFilaPropostaConcluidaMsProposta())
                .to(criaFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding bindingPropostaConcluidaMsNotificacao() {
        return BindingBuilder
                .bind(criarFilaPropostaConcluidaMsNotificacao())
                .to(criaFanoutExchangePropostaConcluida());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate restTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setBeanName("restTemplate");
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }
}
