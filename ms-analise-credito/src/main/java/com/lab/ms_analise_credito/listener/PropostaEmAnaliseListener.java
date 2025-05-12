package com.lab.ms_analise_credito.listener;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.service.AnaliseCreditoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaEmAnaliseListener {

    private static final Logger log = LoggerFactory.getLogger(PropostaEmAnaliseListener.class);

    private final AnaliseCreditoService analiseCreditoService;

    public PropostaEmAnaliseListener(AnaliseCreditoService analiseCreditoService) {
        this.analiseCreditoService = analiseCreditoService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaEmAnalise(Proposta proposta) {
        log.info("Iniciando análise da proposta.");
        analiseCreditoService.analisar(proposta);
        log.info("Análise da proposta finalizada.");
    }
}
