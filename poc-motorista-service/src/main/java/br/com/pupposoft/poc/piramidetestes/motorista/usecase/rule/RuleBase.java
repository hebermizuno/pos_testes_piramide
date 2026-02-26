package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import java.util.Optional;

import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.OutputDto;

public interface RuleBase {

	Optional<OutputDto> validate(InputDto inputDto);

}
