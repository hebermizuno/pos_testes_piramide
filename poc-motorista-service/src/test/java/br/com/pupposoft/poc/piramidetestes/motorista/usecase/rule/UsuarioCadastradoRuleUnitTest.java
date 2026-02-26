package br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaExistenteException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.MotoristaGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.rule.dto.InputDto;

@ExtendWith(MockitoExtension.class)
class UsuarioCadastradoRuleUnitTest {

	@InjectMocks
    private MotoristaCadastradoRule usuarioCadastradoRule;

	@Mock
	private MotoristaGateway usuarioGateway;

	@Test
	void deveValidarComSucesso(){

		final var cpf = "any-cpf";
		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(cpf).when(motoristaMock).getCpf();
		doReturn(Optional.empty()).when(usuarioGateway).obterPorCpf(cpf);

		var output = usuarioCadastradoRule.validate(new InputDto(motoristaMock));

		assertTrue(output.isEmpty());
	}

	@Test
	void deveValidarUsuarioExistenteException() {

		final var cpf = "any-cpf";
		final Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(cpf).when(motoristaMock).getCpf();

		final Motorista motoristaExistentMock = Mockito.mock(Motorista.class);
		doReturn(Optional.of(motoristaExistentMock)).when(usuarioGateway).obterPorCpf(cpf);

		var inputDto = new InputDto(motoristaMock);

		assertThrows(MotoristaExistenteException.class, () -> usuarioCadastradoRule.validate(inputDto)) ;

	}

}
