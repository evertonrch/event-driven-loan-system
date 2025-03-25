package com.lab.ms_notificacao.listener;

import com.lab.ms_notificacao.constantes.MensagemConstante;
import com.lab.ms_notificacao.domain.Proposta;
import com.lab.ms_notificacao.service.NotificarService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PropostaPendenteListener {

    private final NotificarService notificarService;

    public PropostaPendenteListener(NotificarService notificarService) {
        this.notificarService = notificarService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.proposta.pendente}"})
    public void propostaPendente(Proposta proposta) {
        String nomeCapitalizado = StringUtils.capitalize(proposta.getUsuario().getNome());
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, nomeCapitalizado);

        String telefone = proposta.getUsuario().getTelefone();
        if (!telefone.startsWith("+")) {
            telefone = "+".concat(telefone);
        }
        notificarService.notificar(telefone, mensagem);
    }
}
