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
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaComAutomovelAntigoException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;

@ExtendWith(MockitoExtension.class)
class UsuarioComCarroObsoletoRuleUnitTest {

	@InjectMocks
    private MotoristaComCarroObsoletoRule usuarioComCarroObsoletoRule;

	@Test
	void deveValidarComSucesso(){

		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(false).when(motoristaMock).temCarroAntigo();

		var output = usuarioComCarroObsoletoRule.validate(new InputDto(motoristaMock));

		assertTrue(output.isEmpty());
	}

	@Test
	void deveValidarUsuarioExistenteException() {

		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(true).when(motoristaMock).temCarroAntigo();

		var inputDto = new InputDto(motoristaMock);

		assertThrows(MotoristaComAutomovelAntigoException.class, () -> usuarioComCarroObsoletoRule.validate(inputDto)) ;

	}

}
