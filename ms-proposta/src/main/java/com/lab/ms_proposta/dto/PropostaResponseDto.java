package com.lab.ms_proposta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaResponseDto {

    private Long id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String cpf;
    private BigDecimal renda;
    private String valorSolicitadoFmt;
    private Short prazoPagamento;
    private Boolean aprovado;
    private String observacao;

}
