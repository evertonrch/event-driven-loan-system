package com.lab.ms_notificacao.listener;

import com.lab.ms_notificacao.constantes.MensagemConstante;
import com.lab.ms_notificacao.domain.Proposta;
import com.lab.ms_notificacao.domain.Usuario;
import com.lab.ms_notificacao.exception.TelefoneInvalidoException;
import com.lab.ms_notificacao.rule.impl.NormalizaTelefoneRule;
import com.lab.ms_notificacao.rule.impl.ValidaTelefoneRule;
import com.lab.ms_notificacao.service.NotificarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class PropostaConcluidaListener {

    private static final Logger LOG = LoggerFactory.getLogger(PropostaConcluidaListener.class);

    private final NotificarService notificarService;
    private final NormalizaTelefoneRule normalizaTelefoneRule;
    private final ValidaTelefoneRule validaTelefoneRule;

    public PropostaConcluidaListener(NotificarService notificarService, NormalizaTelefoneRule normalizaTelefoneRule, ValidaTelefoneRule validaTelefoneRule) {
        this.notificarService = notificarService;
        this.normalizaTelefoneRule = normalizaTelefoneRule;
        this.validaTelefoneRule = validaTelefoneRule;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.proposta.concluida}"})
    public void propostaConcluida(Proposta proposta) {
        Usuario usuario = proposta.getUsuario();
        try {
            validaTelefoneRule.validar(usuario.getTelefone());
        } catch (TelefoneInvalidoException ex) {
            LOG.warn("Telefone inválido recebido: {}", ex.toString());
            return;
        }

        String telefone = normalizaTelefoneRule.normalizar(usuario.getTelefone());
        String mensagemFormatada = MensagemConstante.PROPOSTA_CONCLUIDA.formatted(StringUtils.capitalize(usuario.getNome()));

        String mensagem = Objects.nonNull(proposta.getObservacao()) ? proposta.getObservacao() : mensagemFormatada;
        notificarService.notificar(telefone, mensagem);
    }
}
