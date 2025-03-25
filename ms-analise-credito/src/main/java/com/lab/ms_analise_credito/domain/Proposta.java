package com.lab.ms_analise_credito.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Proposta {

    private Long id;
    private BigDecimal valorSolicitado;
    private Short prazoPagamento;
    private Boolean aprovado;
    private boolean integrada;
    private String observacao;
    private Usuario usuario;

}