package br.com.pupposoft.poc.piramidetestes.motorista.gateway;

import java.util.List;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Incidente;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;

public interface IncidenteGateway {
	List<Incidente> obterPorMotorista(Motorista motorista);
}
