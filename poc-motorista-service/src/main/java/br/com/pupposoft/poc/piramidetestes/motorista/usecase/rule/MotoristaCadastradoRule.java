package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaExistenteException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.MotoristaGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.OutputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MotoristaCadastradoRule implements RuleBase {

	private final MotoristaGateway usuarioGateway;

	@Override
	public Optional<OutputDto> validate(InputDto inputDto) {

		Motorista novoUsuario = inputDto.getNovoMotorista();

		Optional<Motorista> usuarioOp = usuarioGateway.obterPorCpf(novoUsuario.getCpf());
		if(usuarioOp.isPresent()) {
			log.warn("Motorista ja existe com cpf informado. {}", novoUsuario.getCpf());
			throw new MotoristaExistenteException();
		}

		return Optional.empty();
	}

}
