package br.com.pupposoft.poc.piramidetestes.motorista.gateway;

import java.util.Optional;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;

public interface MotoristaGateway {
	Long criar(Motorista usuario);

	Optional<Motorista> obterPorCpf(String cpf);
}
