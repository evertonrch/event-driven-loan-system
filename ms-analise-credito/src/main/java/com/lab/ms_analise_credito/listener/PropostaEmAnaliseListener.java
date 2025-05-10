package com.lab.ms_analise_credito.listener;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.service.AnaliseCreditoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropostaEmAnaliseListener {

    private static final Logger log = LoggerFactory.getLogger(PropostaEmAnaliseListener.class);

    private final AnaliseCreditoService analiseCreditoService;
    private final RabbitTemplate rabbitTemplate;

    private final String exchangePropostaConcluida;

    public PropostaEmAnaliseListener(AnaliseCreditoService analiseCreditoService,
                                     RabbitTemplate rabbitTemplate,
                                     @Value("${rabbitmq.propostaconcluida.exchange}") String exchangePropostaConcluida) {
        this.analiseCreditoService = analiseCreditoService;
        this.rabbitTemplate = rabbitTemplate;
        this.exchangePropostaConcluida = exchangePropostaConcluida;
    }

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaEmAnalise(Proposta proposta) {
        log.info("Iniciando análise da proposta.");
        analiseCreditoService.analisar(proposta);
        log.info("Análise da proposta finalizada.");

        rabbitTemplate.convertAndSend(exchangePropostaConcluida, "", proposta);
    }
}
