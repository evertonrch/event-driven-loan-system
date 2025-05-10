package com.lab.ms_proposta.agendador;

import com.lab.ms_proposta.entity.Proposta;
import com.lab.ms_proposta.repository.PropostaRepository;
import com.lab.ms_proposta.service.NotificacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;
    private final NotificacaoService notificacaoService;

    @Value("${rabbitmq.proposta.pendente.exchange}")
    private String exchange;

    public PropostaSemIntegracao(PropostaRepository propostaRepository, NotificacaoService notificacaoService) {
        this.propostaRepository = propostaRepository;
        this.notificacaoService = notificacaoService;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao() {
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoService.notificar(proposta, exchange);
                atualizarProposta(proposta);
            } catch (RuntimeException ex) {
                log.error(ex.getMessage());
            }
        });
    }

    private void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }
}
