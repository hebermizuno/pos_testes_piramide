package br.com.pupposoft.poc.piramidetestes.motorista.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Incidente {
	private Long id;
	private StatusIncidente status;

	public boolean estaPendente() {
		return !StatusIncidente.PAGA.equals(status);
	}
}
