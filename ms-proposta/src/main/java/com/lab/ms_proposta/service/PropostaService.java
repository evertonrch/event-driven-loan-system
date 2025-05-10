package com.lab.ms_proposta.service;

import com.lab.ms_proposta.dto.PropostaRequestDto;
import com.lab.ms_proposta.dto.PropostaResponseDto;
import com.lab.ms_proposta.entity.Proposta;
import com.lab.ms_proposta.mapper.PropostaMapper;
import com.lab.ms_proposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final NotificacaoService notificacaoService;

    @Value("${rabbitmq.proposta.pendente.exchange}")
    private String exchange;

    public PropostaService(PropostaRepository propostaRepository, NotificacaoService notificacaoService) {
        this.propostaRepository = propostaRepository;
        this.notificacaoService = notificacaoService;
    }

    public PropostaResponseDto salvar(PropostaRequestDto request) {
        Proposta proposta = PropostaMapper.INSTANCE.fromDtotoProposta(request);
        propostaRepository.save(proposta);
        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.fromEntityToDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDto> obterPropostas() {
        Iterable<Proposta> propostas = propostaRepository.findAll();
        return PropostaMapper.INSTANCE.entityListToDtoList((List<Proposta>) propostas);
    }

    public List<Proposta> obterPropostasSemIntegracao() {
        return propostaRepository.findAllByIntegradaIsFalse();
    }
}
