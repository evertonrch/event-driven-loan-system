package com.lab.ms_analise_credito.strategy.impl;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class PrazoInferiorDezAnos implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return proposta.getPrazoPagamento() <= 120 ? 80 : 0;
    }
}
