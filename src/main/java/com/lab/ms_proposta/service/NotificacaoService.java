package com.lab.ms_proposta.service;

import com.lab.ms_proposta.dto.PropostaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificacaoService {

    private RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponseDto propostaResponseDto, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", propostaResponseDto);
    }
}
