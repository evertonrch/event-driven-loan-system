package com.lab.ms_analise_credito.listener;

import com.lab.ms_analise_credito.domain.Proposta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaEmAnaliseListener {

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaEmAnalise(Proposta proposta) {}
}
