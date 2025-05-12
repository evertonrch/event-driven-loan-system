package com.lab.ms_notificacao.rule;

import com.lab.ms_notificacao.exception.TelefoneInvalidoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidaTelefoneRule {

    private static final Logger LOG = LoggerFactory.getLogger(ValidaTelefoneRule.class);
    private static final Pattern REGEX = Pattern.compile("^55\\d{2}9\\d{8}$");

    public void validar(String telefone) {
        if(!REGEX.matcher(telefone).matches()) {
            LOG.error("formato inválido: {}", telefone);
            throw new TelefoneInvalidoException("O telefone informado está inválido.");
        }
    }
}
