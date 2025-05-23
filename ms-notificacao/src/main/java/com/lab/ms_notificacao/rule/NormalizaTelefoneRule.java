package com.lab.ms_notificacao.rule;

import org.springframework.stereotype.Component;

@Component
public class NormalizaTelefoneRule {

    public String normalizar(String telefone) {
        if (!telefone.startsWith("+")) {
            telefone = "+".concat(telefone);
        }
        return telefone;
    }
}
