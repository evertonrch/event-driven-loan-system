package com.lab.ms_proposta.service;

import com.lab.ms_proposta.dto.PropostaResponseDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    public static final String TOPICO = "/propostas";

    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void notificar(PropostaResponseDto proposta) {
        simpMessagingTemplate.convertAndSend(TOPICO, proposta);
    }
}
