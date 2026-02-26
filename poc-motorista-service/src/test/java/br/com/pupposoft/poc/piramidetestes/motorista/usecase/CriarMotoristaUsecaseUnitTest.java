package br.com.pupposoft.poc.piramidetestes.motorista.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.IncidenteGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.MotoristaGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.RuleBase;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;

@ExtendWith(MockitoExtension.class)
class CriarMotoristaUsecaseUnitTest {

	@InjectMocks
    private CriarMotoristaUsecase criarMotoristaUsecase;

	@Mock
	private MotoristaGateway motoristaGateway;

	@Mock
	private IncidenteGateway incidenteGateway;

	@Test
	void deveCriarComSucesso() {
		final var ruleMock = Mockito.mock(RuleBase.class);
		final var rules = Arrays.asList(ruleMock);
		ReflectionTestUtils.setField(criarMotoristaUsecase, "rules", rules);
		final var idNovoMotorista = 1L;


		Motorista novoMotorista = new Motorista(null, "any name", "any cpf", LocalDate.now(), Arrays.asList(), Arrays.asList());
		doReturn(idNovoMotorista).when(motoristaGateway).criar(novoMotorista);

		Long motoristaId = criarMotoristaUsecase.criar(novoMotorista);

		assertEquals(idNovoMotorista, motoristaId);

		ArgumentCaptor<InputDto> captor = ArgumentCaptor.forClass(InputDto.class);

		verify(ruleMock).validate(captor.capture());

		InputDto inputDtoCaptured = captor.getValue();

		assertEquals(novoMotorista, inputDtoCaptured.getNovoMotorista());

		verify(motoristaGateway).criar(novoMotorista);
	}

	@Test
	void naoDeveCriarPoisFalhouRegra() {
		RuleBase ruleMock = Mockito.mock(RuleBase.class);
		List<RuleBase> rules = Arrays.asList(ruleMock);
		ReflectionTestUtils.setField(criarMotoristaUsecase, "rules", rules);

		doThrow(new RuntimeException("any rules")).when(ruleMock).validate(any((InputDto.class)));

		Motorista novoMotorista = new Motorista(null, "any name", "any cpf", LocalDate.now(), new ArrayList<>(), new ArrayList<>());

		assertThrows(RuntimeException.class, () -> criarMotoristaUsecase.criar(novoMotorista));

		verify(motoristaGateway, never()).criar(novoMotorista);
	}

}
