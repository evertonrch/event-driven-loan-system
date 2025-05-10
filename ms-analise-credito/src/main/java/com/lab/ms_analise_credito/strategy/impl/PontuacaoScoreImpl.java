package com.lab.ms_analise_credito.strategy.impl;

import com.lab.ms_analise_credito.constantes.MensagemConstante;
import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.exception.ScoreBaixoException;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(2)
@Component
public class PontuacaoScoreImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        int score = score();

        if (score < 200) {
            String mensagem = MensagemConstante.PONTUACAO_SERASA_BAIXA.formatted(proposta.getUsuario().getNome());
            throw new ScoreBaixoException(mensagem);
        }

        return switch (score / 200) {
            case 1 -> 150; // score entre 201 e 400
            case 2 -> 180; // score entre 401 e 600
            default -> 200; // score acima de 600
        };
    }

    private int score() {
        return new Random().nextInt(0, 1000);
    }
}
