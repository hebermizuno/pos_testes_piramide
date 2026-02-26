package br.com.pupposoft.poc.piramidetestes.incidente.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.poc.piramidetestes.incidente.controller.json.IncidenteJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IncidenteController {

	@GetMapping("motoristas/{cpf}/incidentes")
	public List<IncidenteJson> obterPorCpf(@PathVariable(name = "cpf") String cpfMotorista) {
		//TODO: Implementar //NOSONAR
		log.warn("### DADOS MOCADOS ###");

		IncidenteJson incidenteA = new IncidenteJson(15L, cpfMotorista, LocalDateTime.of(2023,  Month.MARCH, 15, 8, 45), 258D, "PAGA");
		IncidenteJson incidenteB = new IncidenteJson(16L, cpfMotorista, LocalDateTime.of(2024,  Month.DECEMBER, 1, 18, 30), 258D, "PAGA");

		return Arrays.asList(incidenteA, incidenteB);
	}
}
