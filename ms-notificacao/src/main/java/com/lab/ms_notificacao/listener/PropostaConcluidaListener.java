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

import java.math.BigDecimal;

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
        String mensagem = geraMensagem(proposta.getAprovada(), proposta.getValorSolicitado(), usuario.getNome());

        notificarService.notificar(telefone, mensagem);
    }

    private String geraMensagem(boolean isAprovada, BigDecimal valorSolicitado, String nomeUsuario) {
        String mensagem;
        if(isAprovada) {
            mensagem = MensagemConstante.PROPOSTA_APROVADA.formatted(nomeUsuario, valorSolicitado);
        } else {
            mensagem = MensagemConstante.PROPOSTA_RECUSADA.formatted(nomeUsuario, valorSolicitado);
        }
        return mensagem;
    }
}
