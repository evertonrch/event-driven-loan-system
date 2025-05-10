package com.lab.ms_analise_credito.service;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.exception.StrategyException;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    private static final Logger log = LoggerFactory.getLogger(AnaliseCreditoService.class);

    private final List<CalculoPonto> pontosCalculados;
    private final NotificacaoRabbitService notificacaoRabbitService;
    private static final int PONTUACAO_MINIMA = 350;

    public AnaliseCreditoService(List<CalculoPonto> pontosCalculados, NotificacaoRabbitService notificacaoRabbitService) {
        this.pontosCalculados = pontosCalculados;
        this.notificacaoRabbitService = notificacaoRabbitService;
    }

    public void analisar(Proposta proposta) {
        try {
            int pontos = pontosCalculados.stream().mapToInt(impl -> impl.calcular(proposta)).sum();
            proposta.setAprovada(pontos > PONTUACAO_MINIMA);
            log.info("Proposta aprovada com {} pontos", pontos);
        } catch (StrategyException ex) {
            proposta.setAprovada(false);
            proposta.setObservacao(ex.getMessage());
            log.warn("Proposta recusada.");
        }

        notificacaoRabbitService.notificar(proposta);
    }
}
