package com.lab.ms_proposta.controller;

import com.lab.ms_proposta.dto.PropostaRequestDto;
import com.lab.ms_proposta.dto.PropostaResponseDto;
import com.lab.ms_proposta.service.PropostaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/proposta")
@AllArgsConstructor
public class PropostaController {

    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> salvar(@RequestBody PropostaRequestDto request) {
        PropostaResponseDto responseDto = propostaService.salvar(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }
}
