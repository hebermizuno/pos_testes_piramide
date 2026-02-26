package br.com.pupposoft.poc.piramidetestes.motorista.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Motorista {
	private Long id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private List<Automovel> automoveis;
	private List<Incidente> incidentes;

	public Long getIdade() {
		return dataNascimento.until(LocalDate.now(), ChronoUnit.YEARS);
	}

	public boolean isMenorIdade() {
		return getIdade() < 18;
	}

	public boolean semAutomovel() {
		return automoveis.isEmpty();
	}

	public boolean temCarroAntigo() {
		return automoveis.stream().anyMatch(Automovel::isAntigo);
	}

	public void atualizaIncidentes(List<Incidente> incidentes) {
		if(incidentes == null) {
			incidentes = new ArrayList<>();
		}
		this.incidentes.addAll(incidentes);
	}

	public boolean temIncidentesPendente() {
		return incidentes.stream().anyMatch(Incidente::estaPendente);
	}
}
