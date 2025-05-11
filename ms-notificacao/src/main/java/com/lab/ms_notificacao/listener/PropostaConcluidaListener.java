package com.lab.ms_notificacao.listener;

import com.lab.ms_notificacao.constantes.MensagemConstante;
import com.lab.ms_notificacao.domain.Proposta;
import com.lab.ms_notificacao.service.NotificarService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class PropostaConcluidaListener {

    private final NotificarService notificarService;

    public PropostaConcluidaListener(NotificarService notificarService) {
        this.notificarService = notificarService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.proposta.concluida}"})
    public void propostaConcluida(Proposta proposta) {
        String telefone = proposta.getUsuario().getTelefone();
        if (!telefone.startsWith("+")) {
            telefone = "+".concat(telefone);
        }

        String formataddo = MensagemConstante.PROPOSTA_CONCLUIDA.formatted(StringUtils.capitalize(proposta.getUsuario().getNome()));
        notificarService.notificar(telefone, Objects.nonNull(proposta.getObservacao()) ? proposta.getObservacao() : formataddo);
    }
}
