package br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.AutomovelEntity;

public interface AutomovelRepository extends JpaRepository<AutomovelEntity, Long> {

}
