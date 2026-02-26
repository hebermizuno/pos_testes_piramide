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
import br.com.pupposoft.poc.piramidetestes.motorista.exception.UsuarioSemAutomovelCadastradoException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;

@ExtendWith(MockitoExtension.class)
class UsuarioSemAutomovelRuleUnitTest {

	@InjectMocks
    private MotoristaSemAutomovelRule usuarioSemAutomovelRule;

	@Test
	void deveValidarComSucesso(){

		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(false).when(motoristaMock).semAutomovel();

		var output = usuarioSemAutomovelRule.validate(new InputDto(motoristaMock));

		assertTrue(output.isEmpty());
	}

	@Test
	void deveValidarUsuarioSemAutomovelCadastradoException() {

		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(true).when(motoristaMock).semAutomovel();

		var inputDto = new InputDto(motoristaMock);

		assertThrows(UsuarioSemAutomovelCadastradoException.class, () -> usuarioSemAutomovelRule.validate(inputDto)) ;
	}

}
