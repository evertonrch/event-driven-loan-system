package com.lab.ms_analise_credito.service;

import com.lab.ms_analise_credito.domain.Proposta;
import com.lab.ms_analise_credito.strategy.CalculoPonto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    private final List<CalculoPonto> pontosCalculados;

    public AnaliseCreditoService(List<CalculoPonto> pontosCalculados) {
        this.pontosCalculados = pontosCalculados;
    }

    public void analisar(Proposta proposta) {
        int pontos = pontosCalculados.stream().mapToInt(impl-> impl.calcular(proposta)).sum();

    }
}
