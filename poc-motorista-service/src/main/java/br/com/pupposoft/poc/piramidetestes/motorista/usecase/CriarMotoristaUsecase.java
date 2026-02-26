package br.com.pupposoft.poc.piramidetestes.motorista.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Incidente;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.IncidenteGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.MotoristaGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.RuleBase;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarMotoristaUsecase {

	private final MotoristaGateway usuarioGateway;

	private final IncidenteGateway incidenteGateway;

	private final List<RuleBase> rules;

	public Long criar(Motorista novoMotorista) {
		obtemIncidentes(novoMotorista);

		validaRegras(novoMotorista);

		return usuarioGateway.criar(novoMotorista);
	}

	private void validaRegras(Motorista novoMotorista) {
		var inputDto = new InputDto(novoMotorista);
		rules.forEach(r -> r.validate(inputDto));
	}

	private void obtemIncidentes(Motorista novoUsuario) {
		List<Incidente> incidentes = incidenteGateway.obterPorMotorista(novoUsuario);
		novoUsuario.atualizaIncidentes(incidentes);
	}
}
