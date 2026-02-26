package br.com.pupposoft.poc.piramidetestes.incidente.controller.json;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncidenteJson {
	private Long id;
	private String cpfMotorista;
	private LocalDateTime dataHora;
	private Double valor;
	private String status;
}
