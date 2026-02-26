package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaMenorIdadeException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.OutputDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MotoristaMenorIdadeRule implements RuleBase {

	@Override
	public Optional<OutputDto> validate(InputDto inputDto) {

		Motorista novoUsuario = inputDto.getNovoMotorista();

		if(novoUsuario.isMenorIdade()) {
			log.warn("Motorista menor de idade. idade={}", novoUsuario.getIdade());
			throw new MotoristaMenorIdadeException();
		}

		return Optional.empty();
	}
}
