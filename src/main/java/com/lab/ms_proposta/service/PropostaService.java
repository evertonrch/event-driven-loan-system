package com.lab.ms_proposta.service;

import com.lab.ms_proposta.dto.PropostaRequestDto;
import com.lab.ms_proposta.dto.PropostaResponseDto;
import com.lab.ms_proposta.entity.Proposta;
import com.lab.ms_proposta.mapper.PropostaMapper;
import com.lab.ms_proposta.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropostaService {

    private PropostaRepository propostaRepository;
    private NotificacaoService notificacaoService;

    public PropostaResponseDto salvar(PropostaRequestDto request) {
        Proposta proposta = PropostaMapper.INSTANCE.fromDtotoProposta(request);
        Proposta propostaSalva = propostaRepository.save(proposta);

        var propostaResponse = PropostaMapper.INSTANCE.fromEntityToDto(propostaSalva);
        notificacaoService.notificar(propostaResponse, "proposta-pendente.ex");

        return propostaResponse;
    }

    public List<PropostaResponseDto> obterPropostas() {
        Iterable<Proposta> propostas = propostaRepository.findAll();
        return PropostaMapper.INSTANCE.entityListToDtoList((List<Proposta>) propostas);
    }
}
