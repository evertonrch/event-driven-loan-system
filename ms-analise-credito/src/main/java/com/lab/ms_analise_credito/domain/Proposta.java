package com.lab.ms_analise_credito.domain;

import java.math.BigDecimal;

public class Proposta {

    private Long id;
    private BigDecimal valorSolicitado;
    private Short prazoPagamento;
    private Boolean aprovada;
    private boolean integrada;
    private String observacao;
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public Short getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Short prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public Boolean getAprovada() {
        return aprovada;
    }

    public void setAprovada(Boolean aprovada) {
        this.aprovada = aprovada;
    }

    public boolean isIntegrada() {
        return integrada;
    }

    public void setIntegrada(boolean integrada) {
        this.integrada = integrada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}