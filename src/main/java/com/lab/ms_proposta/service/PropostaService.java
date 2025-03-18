package com.lab.ms_proposta.service;

import com.lab.ms_proposta.dto.PropostaRequestDto;
import com.lab.ms_proposta.dto.PropostaResponseDto;
import com.lab.ms_proposta.entity.Proposta;
import com.lab.ms_proposta.mapper.PropostaMapper;
import com.lab.ms_proposta.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropostaService {

    private PropostaRepository propostaRepository;


    public PropostaResponseDto salvar(PropostaRequestDto request) {
        Proposta proposta = PropostaMapper.INSTANCE.fromDtotoProposta(request);
        Proposta propostaSalva = propostaRepository.save(proposta);

        return PropostaMapper.INSTANCE.fromEntityToDto(propostaSalva);
    }
}
