package com.lab.ms_analise_credito.service;

import com.lab.ms_analise_credito.domain.Proposta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoRabbitService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    public NotificacaoRabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notificar(Proposta proposta) {
        rabbitTemplate.convertAndSend(exchangePropostaConcluida, "", proposta);
    }
}
