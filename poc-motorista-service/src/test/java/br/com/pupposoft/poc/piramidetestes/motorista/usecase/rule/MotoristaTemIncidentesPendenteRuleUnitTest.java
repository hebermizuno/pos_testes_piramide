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
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaTemIncidentesPendenteException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;

@ExtendWith(MockitoExtension.class)
class MotoristaTemIncidentesPendenteRuleUnitTest {

	@InjectMocks
    private MotoristaTemIncidentesPendenteRule motoristaTemIncidentesPendenteRule;


	@Test
	void deveValidarComSucesso(){

		Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(false).when(motoristaMock).temIncidentesPendente();
		var output = motoristaTemIncidentesPendenteRule.validate(new InputDto(motoristaMock));

		assertTrue(output.isEmpty());
	}

	@Test
	void deveValidarComMotoristaTemIncidentesPendenteException(){

		Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(true).when(motoristaMock).temIncidentesPendente();

		var inputDto = new InputDto(motoristaMock);

		assertThrows(MotoristaTemIncidentesPendenteException.class, () -> motoristaTemIncidentesPendenteRule.validate(inputDto));
	}


}
