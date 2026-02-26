package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaMenorIdadeException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;

@ExtendWith(MockitoExtension.class)
class UsuarioMenorIdadeRuleUnitTest {

	@InjectMocks
    private MotoristaMenorIdadeRule usuarioMenorIdadeRule;

	@Test
	void deveValidarComSucesso(){

		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(false).when(motoristaMock).isMenorIdade();

		var output = usuarioMenorIdadeRule.validate(new InputDto(motoristaMock));

		assertTrue(output.isEmpty());
	}

	@Test
	void deveValidarUsuarioMenorIdadeException() {

		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(true).when(motoristaMock).isMenorIdade();

		var inputDto = new InputDto(motoristaMock);

		assertThrows(MotoristaMenorIdadeException.class, () -> usuarioMenorIdadeRule.validate(inputDto)) ;
	}

}
