package br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Automovel;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.ErroAoAcessarRepositorioException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.MotoristaEntity;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository.AutomovelRepository;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository.MotoristaRepository;

@ExtendWith(MockitoExtension.class)
class MotoristaJpaGatewayUnitTest {

	@InjectMocks
    private MotoristaJpaGateway motoristaJpaGateway;

	@Mock
	private AutomovelRepository automovelRepository;

	@Mock
	private MotoristaRepository usuarioRepository;

	@Test
	void deveCriarcomSucesso() {
		var dataNascimento = LocalDate.now();
		var motoristaIdToCreate = 15L;


		var automovel = new Automovel(null, "any-modelo", LocalDate.now(), null);
		var motorista = new Motorista(null, "any-name", "any-cpf", dataNascimento, Arrays.asList(automovel), Arrays.asList());

		var motoristaEntityMock = Mockito.mock(MotoristaEntity.class);
		doReturn(motoristaIdToCreate).when(motoristaEntityMock).getId();
		doReturn(motoristaEntityMock).when(usuarioRepository).save(any(MotoristaEntity.class));

		var motoristaIdCreated = motoristaJpaGateway.criar(motorista);

		assertEquals(motoristaIdToCreate, motoristaIdCreated);

		var motoristaEntityAC = ArgumentCaptor.forClass(MotoristaEntity.class);
		verify(usuarioRepository).save(motoristaEntityAC.capture());

		var motoristaEntityCaptured = motoristaEntityAC.getValue();

		assertEquals(motorista.getNome(), motoristaEntityCaptured.getNome());
		assertEquals(motorista.getCpf(), motoristaEntityCaptured.getCpf());
		assertEquals(motorista.getDataNascimento(), motoristaEntityCaptured.getDataNascimento());

		assertEquals(1, motoristaEntityCaptured.getAutomoveis().size());
		var automovelEntityCaptured = motoristaEntityCaptured.getAutomoveis().get(0);

		assertEquals(automovel.getId(), automovelEntityCaptured.getId());
		assertEquals(automovel.getModelo(), automovelEntityCaptured.getModelo());
		assertEquals(automovel.getDataModelo(), automovelEntityCaptured.getDataModelo());
		assertEquals(motoristaEntityCaptured, automovelEntityCaptured.getUsuario());
	}

	@Test
	void naoDeveCriarDevidoAoErroAoAcessarRepositorioException() {
		var motorista = new Motorista(null, null, null, null, Arrays.asList(), Arrays.asList());
		doThrow(new RuntimeException()).when(usuarioRepository).save(any(MotoristaEntity.class));
		assertThrows(ErroAoAcessarRepositorioException.class, () -> motoristaJpaGateway.criar(motorista));
	}

	@Test
	void deveObterMotoristaPorCpf() {
		final var cpf = "any-cpf";
		final var motoristaEntity = new MotoristaEntity(1L, cpf, "any-name", LocalDate.now(), Arrays.asList());
		doReturn(Optional.of(motoristaEntity)).when(usuarioRepository).findByCpf(cpf);

		var motoristaOP = motoristaJpaGateway.obterPorCpf(cpf);

		assertTrue(motoristaOP.isPresent());

		var motorista = motoristaOP.get();

		assertEquals(motoristaEntity.getId(), motorista.getId());
		assertEquals(motoristaEntity.getNome(), motorista.getNome());
		assertEquals(motoristaEntity.getCpf(), motorista.getCpf());
		assertEquals(motoristaEntity.getDataNascimento(), motorista.getDataNascimento());
		assertTrue(motoristaEntity.getAutomoveis().isEmpty());

		verify(usuarioRepository).findByCpf(cpf);
	}

	@Test
	void naoDeveObterMotoristaPorCpfPoisNaoExiste() {
		var cpf = "any-cpf";
		doReturn(Optional.empty()).when(usuarioRepository).findByCpf(cpf);

		var motoristaOP = motoristaJpaGateway.obterPorCpf(cpf);

		assertTrue(motoristaOP.isEmpty());
		verify(usuarioRepository).findByCpf(cpf);
	}

	@Test
	void naoDeveObterMotoristaPorCpfPoisOcorreErroAoAcessarRepositorioException() {
		var cpf = "any-cpf";
		doThrow(new RuntimeException()).when(usuarioRepository).findByCpf(cpf);

		assertThrows(ErroAoAcessarRepositorioException.class, () -> motoristaJpaGateway.obterPorCpf(cpf));
		verify(usuarioRepository).findByCpf(cpf);
	}
}
