package br.com.pupposoft.poc.piramidetestes.motorista.controller.json;

import java.time.LocalDate;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Automovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutomovelJson {
	private Long id;
	private String modelo;
	private LocalDate dataModelo;

	public Automovel mapToDomain() {
		return new Automovel(id, modelo, dataModelo, null);
	}
}
