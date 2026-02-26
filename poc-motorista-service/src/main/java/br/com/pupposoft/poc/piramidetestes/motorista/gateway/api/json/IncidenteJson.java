package br.com.pupposoft.poc.piramidetestes.motorista.gateway.api.json;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Incidente;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.StatusIncidente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenteJson {
	private Long id;
	private String status;

	public Incidente mapToDomain() {
		return new Incidente(id, StatusIncidente.valueOf(status));
	}
}
