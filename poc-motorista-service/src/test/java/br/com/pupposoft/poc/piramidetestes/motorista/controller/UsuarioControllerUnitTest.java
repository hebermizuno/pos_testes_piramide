package br.com.pupposoft.poc.piramidetestes.motorista.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.AutomovelJson;
import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.MotoristaJson;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Automovel;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.CriarMotoristaUsecase;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerUnitTest {

	@InjectMocks
    private MotoristaController usuarioController;

	@Mock
    private CriarMotoristaUsecase criarUsuarioUsecase;

	@Test
	void deveCriarComSucesso() {

		final Long userIdToCreated = 1L;

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", LocalDate.of(2024, Month.JANUARY, 16));

		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", "any cpf", LocalDate.of(1980, Month.JANUARY, 16), Arrays.asList(automovel));

		doReturn(userIdToCreated).when(criarUsuarioUsecase).criar(any(Motorista.class));

		Long userId = usuarioController.criar(motoristaJson);

		assertEquals(userIdToCreated, userId);

		ArgumentCaptor<Motorista> captor = ArgumentCaptor.forClass(Motorista.class);

		verify(criarUsuarioUsecase).criar(captor.capture());

		Motorista userCaptured = captor.getValue();

		assertNull(userCaptured.getId());
		assertEquals(motoristaJson.getNome(), userCaptured.getNome());
		assertEquals(motoristaJson.getCpf(), userCaptured.getCpf());
		assertEquals(motoristaJson.getDataNascimento(), userCaptured.getDataNascimento());

		List<Automovel> automoveisCapturado = userCaptured.getAutomoveis();

		assertEquals(1, automoveisCapturado.size());
		assertEquals(automovel.getId(), automoveisCapturado.get(0).getId());
		assertEquals(automovel.getModelo(), automoveisCapturado.get(0).getModelo());
		assertEquals(automovel.getDataModelo(), automoveisCapturado.get(0).getDataModelo());
	}

}
