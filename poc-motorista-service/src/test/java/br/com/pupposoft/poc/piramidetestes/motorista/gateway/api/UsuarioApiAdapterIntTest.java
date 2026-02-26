package br.com.pupposoft.poc.piramidetestes.motorista.gateway.api;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Incidente;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.StatusIncidente;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.AcessoIncidenteServiceException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.IncidenteGateway;

@WireMockTest
class UsuarioApiAdapterIntTest {

	@Test
	void deveObterListaIncidentesComSucesso(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "any-cpf";
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
		final var responseBodyStr = "[{\"id\": 15, \"status\":\"ABERTA\" }]";

		var webClientBuilder = WebClient.builder();
		var usuarioApiAdapter = new UsuarioApiAdapter(webClientBuilder);
		setField(usuarioApiAdapter, "usuarioBaseUrl", baseUrl);

		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(okJson(responseBodyStr)));

		Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(cpf).when(motoristaMock).getCpf();

		var incidentes = usuarioApiAdapter.obterPorMotorista(motoristaMock);

		assertEquals(1, incidentes.size());


		Incidente incidente = incidentes.get(0);
		assertEquals(15, incidente.getId());
		assertEquals(StatusIncidente.ABERTA, incidente.getStatus());
	}

	@Test
	void deveObterAcessoIncidenteServiceException(WireMockRuntimeInfo wireMockRuntimeInfo) {
		final var cpf = "any-cpf";
		final var baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();

		WebClient.Builder webClientBuilder = WebClient.builder();
		IncidenteGateway usuarioApiAdapter = new UsuarioApiAdapter(webClientBuilder);
		setField(usuarioApiAdapter, "usuarioBaseUrl", baseUrl);

		stubFor(get("/motoristas/" + cpf + "/incidentes").willReturn(serverError()));

		Motorista motoristaMock = Mockito.mock(Motorista.class);
		doReturn(cpf).when(motoristaMock).getCpf();

		assertThrows(AcessoIncidenteServiceException.class, () -> usuarioApiAdapter.obterPorMotorista(motoristaMock));
	}

}
