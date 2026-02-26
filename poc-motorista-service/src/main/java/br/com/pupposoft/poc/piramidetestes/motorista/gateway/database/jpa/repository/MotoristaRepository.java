package br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.MotoristaEntity;

public interface MotoristaRepository extends JpaRepository<MotoristaEntity, Long>{

	Optional<MotoristaEntity> findByCpf(String cpf);

}
