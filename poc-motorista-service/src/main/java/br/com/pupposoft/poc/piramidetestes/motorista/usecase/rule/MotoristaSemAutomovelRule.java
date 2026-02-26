package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.UsuarioSemAutomovelCadastradoException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.OutputDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MotoristaSemAutomovelRule implements RuleBase {

	@Override
	public Optional<OutputDto> validate(InputDto inputDto) {
		Motorista novoUsuario = inputDto.getNovoMotorista();

		if(novoUsuario.semAutomovel()) {
			log.warn("Usuário sem automovel");
			throw new UsuarioSemAutomovelCadastradoException();
		}

		return Optional.empty();
	}

}
