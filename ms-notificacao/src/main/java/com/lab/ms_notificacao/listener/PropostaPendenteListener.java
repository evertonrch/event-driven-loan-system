package com.lab.ms_notificacao.listener;

import com.lab.ms_notificacao.constantes.MensagemConstante;
import com.lab.ms_notificacao.domain.Proposta;
import com.lab.ms_notificacao.domain.Usuario;
import com.lab.ms_notificacao.exception.TelefoneInvalidoException;
import com.lab.ms_notificacao.rule.NormalizaTelefoneRule;
import com.lab.ms_notificacao.rule.ValidaTelefoneRule;
import com.lab.ms_notificacao.service.NotificarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PropostaPendenteListener {

    private static final Logger LOG = LoggerFactory.getLogger(PropostaPendenteListener.class);

    private final NotificarService notificarService;
    private final NormalizaTelefoneRule normalizaTelefoneRule;
    private final ValidaTelefoneRule validaTelefoneRule;

    public PropostaPendenteListener(NotificarService notificarService, NormalizaTelefoneRule normalizaTelefoneRule, ValidaTelefoneRule validaTelefoneRule) {
        this.notificarService = notificarService;
        this.normalizaTelefoneRule = normalizaTelefoneRule;
        this.validaTelefoneRule = validaTelefoneRule;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.proposta.pendente}"})
    public void propostaPendente(Proposta proposta) {
        Usuario usuario = proposta.getUsuario();
        String nomeCapitalizado = StringUtils.capitalize(usuario.getNome());
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, nomeCapitalizado);

        String telefone = usuario.getTelefone();
        try {
            validaTelefoneRule.validar(telefone);
        } catch (TelefoneInvalidoException ex) {
            LOG.warn("Telefone inválido recebido: {}", ex.toString());
            return;
        }

        telefone = normalizaTelefoneRule.normalizar(telefone);
        notificarService.notificar(telefone, mensagem);
    }
}
