package com.lab.ms_proposta.listener;

import com.lab.ms_proposta.dto.PropostaResponseDto;
import com.lab.ms_proposta.entity.Proposta;
import com.lab.ms_proposta.mapper.PropostaMapper;
import com.lab.ms_proposta.repository.PropostaRepository;
import com.lab.ms_proposta.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    private final PropostaRepository propostaRepository;
    private final WebSocketService webSocketService;

    public PropostaConcluidaListener(PropostaRepository propostaRepository, WebSocketService webSocketService) {
        this.propostaRepository = propostaRepository;
        this.webSocketService = webSocketService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        propostaRepository.save(proposta);
        webSocketService.notificar(toDto(proposta));
    }

    private PropostaResponseDto toDto(Proposta proposta) {
        return PropostaMapper.INSTANCE.fromEntityToDto(proposta);
    }
}
