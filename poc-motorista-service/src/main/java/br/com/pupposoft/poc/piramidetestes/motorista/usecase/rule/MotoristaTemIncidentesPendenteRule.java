package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaTemIncidentesPendenteException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.OutputDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MotoristaTemIncidentesPendenteRule implements RuleBase {

	@Override
	public Optional<OutputDto> validate(InputDto inputDto) {
		Motorista novoMotorista = inputDto.getNovoMotorista();

		if(novoMotorista.temIncidentesPendente()) {
			log.warn("Motorista tem incidentes pendente");
			throw new MotoristaTemIncidentesPendenteException();
		}

		return Optional.empty();
	}

}
