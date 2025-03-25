package com.lab.ms_analise_credito.strategy.impl;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RendaMaiorValorSolicitado implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorQueValorSolicitado(proposta) ? 100 : 0;

    }

    private boolean rendaMaiorQueValorSolicitado(Proposta proposta) {
        BigDecimal renda = proposta.getUsuario().getRenda();
        return renda.compareTo(proposta.getValorSolicitado()) > 0;
    }
}
