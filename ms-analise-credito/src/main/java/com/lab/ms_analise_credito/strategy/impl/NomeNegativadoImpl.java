package com.lab.ms_analise_credito.strategy.impl;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.exception.NomeNegativadoException;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1)
@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        if (estaNegativado()) {
            throw new NomeNegativadoException("Nome do usuário está negativado.");
        }
        return 100;
    }

    private boolean estaNegativado() {
        return new Random().nextBoolean();
    }
}
