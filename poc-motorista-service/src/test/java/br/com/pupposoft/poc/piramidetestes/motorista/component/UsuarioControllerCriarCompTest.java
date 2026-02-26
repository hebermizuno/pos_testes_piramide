package br.com.pupposoft.poc.piramidetestes.motorista.component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import br.com.pupposoft.poc.piramidetestes.motorista.controller.MotoristaController;
import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.AutomovelJson;
import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.MotoristaJson;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaTemIncidentesPendenteException;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaComAutomovelAntigoException;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaExistenteException;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaMenorIdadeException;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.UsuarioSemAutomovelCadastradoException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.IncidenteGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.MotoristaEntity;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository.MotoristaRepository;
import br.com.pupposoft.poc.piramidetestes.motorista.util.container.AbstractContainer;
import jakarta.transaction.Transactional;

@WireMockTest
@ActiveProfiles("comp-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerCriarCompTest extends AbstractContainer {

	@Autowired
	private MotoristaController usuarioController;

	@Autowired
	private IncidenteGateway incidenteGateway;

	@Autowired
	private MotoristaRepository motoristaRepository;

	@Test
	@Transactional
	void deveCriarMotoristaComSucesso(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(18);
		final var dataLancamentoCarro = LocalDate.now().minusYears(3);
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();


		//Configuração do incidenteGateway
		setField(incidenteGateway, "usuarioBaseUrl", baseUrl);
		final var incidenteResponseBodyMockStr = "[{\"id\": 15, \"status\":\"PAGA\" }]";
		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(incidenteResponseBodyMockStr)));

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", dataLancamentoCarro);
		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento, Arrays.asList(automovel));

		assertEquals(0, motoristaRepository.count());

		Long userId = usuarioController.criar(motoristaJson);

		assertEquals(1, motoristaRepository.count());

		var motoristaEntity = motoristaRepository.findAll().get(0);

		assertEquals(userId, motoristaEntity.getId());
		assertEquals(motoristaJson.getNome(), motoristaEntity.getNome());
		assertEquals(motoristaJson.getCpf(), motoristaEntity.getCpf());
		assertEquals(motoristaJson.getDataNascimento(), motoristaEntity.getDataNascimento());

		assertEquals(1, motoristaEntity.getAutomoveis().size());

		var automovelEntity = motoristaEntity.getAutomoveis().get(0);

		assertEquals(automovel.getModelo(), automovelEntity.getModelo());
		assertEquals(automovel.getDataModelo(), automovelEntity.getDataModelo());
		assertNotNull(automovelEntity.getId());
	}

	@Test
	@Transactional
	void naoDeveCriarMotoristaPoisMenorIdade(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(17);//Menor de idade
		final var dataLancamentoCarro = LocalDate.now().minusYears(3);
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();

		//Configuração do incidenteGateway
		setField(incidenteGateway, "usuarioBaseUrl", baseUrl);
		final var incidenteResponseBodyMockStr = "[{\"id\": 15, \"status\":\"PAGA\" }]";
		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(incidenteResponseBodyMockStr)));

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", dataLancamentoCarro);
		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento , Arrays.asList(automovel));

		assertEquals(0, motoristaRepository.count());

		assertThrows(MotoristaMenorIdadeException.class, () -> usuarioController.criar(motoristaJson));

		assertEquals(0, motoristaRepository.count());
	}

	@Test
	@Transactional
	void naoDeveCriarMotoristaPoisExisteIncidenteAbertos(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(18);
		final var dataLancamentoCarro = LocalDate.now().minusYears(3);
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();


		//Configuração do incidenteGateway
		setField(incidenteGateway, "usuarioBaseUrl", baseUrl);
		final var incidenteResponseBodyMockStr = "[{\"id\": 15, \"status\":\"ABERTA\" }]";
		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(incidenteResponseBodyMockStr)));

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", dataLancamentoCarro);
		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento, Arrays.asList(automovel));

		assertEquals(0, motoristaRepository.count());

		assertThrows(MotoristaTemIncidentesPendenteException.class, () -> usuarioController.criar(motoristaJson));

		assertEquals(0, motoristaRepository.count());

	}

	@Test
	@Transactional
	void naoDeveCriarMotoristaPoisMotoristaComAutomovelAntigo(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(18);
		final var dataLancamentoCarro = LocalDate.now().minusYears(4);//Carro Obsoleto
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();


		//Configuração do incidenteGateway
		setField(incidenteGateway, "usuarioBaseUrl", baseUrl);
		final var incidenteResponseBodyMockStr = "[{\"id\": 15, \"status\":\"PAGA\" }]";
		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(incidenteResponseBodyMockStr)));

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", dataLancamentoCarro);
		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento, Arrays.asList(automovel));

		assertEquals(0, motoristaRepository.count());

		assertThrows(MotoristaComAutomovelAntigoException.class, () -> usuarioController.criar(motoristaJson));

		assertEquals(0, motoristaRepository.count());

	}

	@Test
	@Transactional
	void naoDeveCriarMotoristaPoisMotoristaSemAutomovelCadastrado(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(18);
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();


		//Configuração do incidenteGateway
		setField(incidenteGateway, "usuarioBaseUrl", baseUrl);
		final var incidenteResponseBodyMockStr = "[{\"id\": 15, \"status\":\"PAGA\" }]";
		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(incidenteResponseBodyMockStr)));

		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento, null);

		assertEquals(0, motoristaRepository.count());

		assertThrows(UsuarioSemAutomovelCadastradoException.class, () -> usuarioController.criar(motoristaJson));

		assertEquals(0, motoristaRepository.count());

	}

	@Test
	@Transactional
	void naoDeveCriarMotoristaPoisMotoristaExistenteException(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(18);
		final var dataLancamentoCarro = LocalDate.now().minusYears(3);
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();


		//Configuração do incidenteGateway
		setField(incidenteGateway, "usuarioBaseUrl", baseUrl);
		final var incidenteResponseBodyMockStr = "[{\"id\": 15, \"status\":\"PAGA\" }]";
		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(incidenteResponseBodyMockStr)));

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", dataLancamentoCarro);
		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento, Arrays.asList(automovel));

		MotoristaEntity motoristaExistente = new MotoristaEntity(null, cpf, "Any Name", LocalDate.now(), Arrays.asList());
		motoristaRepository.save(motoristaExistente);
		assertEquals(1, motoristaRepository.count());

		assertThrows(MotoristaExistenteException.class, () -> usuarioController.criar(motoristaJson));

		assertEquals(1, motoristaRepository.count());
	}

	//TODO: adicionar testes de falha ao acessar database e/ou falha ao acessar api de incidentes
}
