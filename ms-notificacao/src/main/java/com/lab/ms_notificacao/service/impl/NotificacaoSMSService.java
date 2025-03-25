package com.lab.ms_notificacao.service.impl;

import com.lab.ms_notificacao.service.MessageTextService;
import com.lab.ms_notificacao.service.NotificarService;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoSMSService implements NotificarService {

    private final MessageTextService messageTextService;

    public NotificacaoSMSService(MessageTextService messageTextService) {
        this.messageTextService = messageTextService;
    }

    @Override
    public void notificar(String telefone, String mensagem) {
        messageTextService.sendSMS(telefone, mensagem);
    }
}
