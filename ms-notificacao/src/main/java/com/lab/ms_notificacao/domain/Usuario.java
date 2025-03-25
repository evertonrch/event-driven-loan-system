package com.lab.ms_notificacao.domain;

import java.math.BigDecimal;


public class Usuario {

    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private BigDecimal renda;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public BigDecimal getRenda() {
        return renda;
    }
}
