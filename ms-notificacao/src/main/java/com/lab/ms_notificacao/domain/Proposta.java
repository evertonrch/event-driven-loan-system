package com.lab.ms_notificacao.domain;

import java.math.BigDecimal;


public class Proposta {

    private Long id;
    private BigDecimal valorSolicitado;
    private Short prazoPagamento;
    private Boolean aprovado;
    private boolean integrada;
    private String observacao;
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public Short getPrazoPagamento() {
        return prazoPagamento;
    }

    public Boolean getAprovado() {
        return aprovado;
    }

    public boolean isIntegrada() {
        return integrada;
    }

    public String getObservacao() {
        return observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}