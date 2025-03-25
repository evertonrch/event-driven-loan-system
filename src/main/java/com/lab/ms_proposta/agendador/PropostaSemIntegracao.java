package com.lab.ms_proposta.agendador;

import com.lab.ms_proposta.entity.Proposta;
import com.lab.ms_proposta.service.NotificacaoService;
import com.lab.ms_proposta.service.PropostaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PropostaSemIntegracao {

    private PropostaService propostaService;
    private NotificacaoService notificacaoService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    public PropostaSemIntegracao(PropostaService propostaService, NotificacaoService notificacaoService) {
        this.propostaService = propostaService;
        this.notificacaoService = notificacaoService;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao() {
        propostaService.obterPropostasSemIntegracao().stream().forEach(proposta -> {
            try {
                notificacaoService.notificar(proposta, exchange);
                atualizarProposta(proposta);
                log.info("Proposta {} atualizada.", proposta.getId());
            } catch (RuntimeException ex) {
                log.error("Erro ao reenviar notificação: {}", ex.getMessage());
            }
        });
    }

    private void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        propostaService.salvar(proposta);
    }
}
