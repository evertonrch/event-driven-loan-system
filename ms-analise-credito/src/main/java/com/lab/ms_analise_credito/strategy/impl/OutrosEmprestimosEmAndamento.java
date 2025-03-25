package com.lab.ms_analise_credito.strategy.impl;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OutrosEmprestimosEmAndamento implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return temOutrosEmprestimos() ? 0 : 80;
    }

    private boolean temOutrosEmprestimos() {
        return new Random().nextBoolean();
    }
}
