package br.com.pupposoft.poc.piramidetestes.motorista.gateway.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Incidente;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.AcessoIncidenteServiceException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.IncidenteGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.api.json.IncidenteJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioApiAdapter implements IncidenteGateway {

    @Value("${usuario-service.base-url}")
    private String usuarioBaseUrl;

    private final WebClient.Builder webClientBuilder;


    @Override
    public List<Incidente> obterPorMotorista(Motorista motorista) {
        try {
            final String url = usuarioBaseUrl + "/motoristas/" + motorista.getCpf() + "/incidentes";

            List<IncidenteJson> incidentesJson = callService(url);

            return incidentesJson.stream().map(IncidenteJson::mapToDomain).toList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AcessoIncidenteServiceException();
        }
    }

    private List<IncidenteJson> callService(String url) {
        WebClient webClient = webClientBuilder.baseUrl(url).build();

        return webClient
                .get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IncidenteJson>>() {})
                .block();

    }
}
