package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaComAutomovelAntigoException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.OutputDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MotoristaComCarroObsoletoRule implements RuleBase {

	@Override
	public Optional<OutputDto> validate(InputDto inputDto) {

		Motorista novoUsuario = inputDto.getNovoMotorista();

		if(novoUsuario.temCarroAntigo()) {
			log.warn("Motorista possui automoveis antigos");
			throw new MotoristaComAutomovelAntigoException();
		}

		return Optional.empty();
	}
}
