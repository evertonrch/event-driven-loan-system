package com.lab.ms_proposta.repository;

import com.lab.ms_proposta.entity.Proposta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    List<Proposta> findAllByIntegradaIsFalse();
}
