package br.com.pupposoft.poc.piramidetestes.motorista.exception;

import lombok.Getter;

@Getter
public class AcessoIncidenteServiceException extends SystemBaseException {
	private static final long serialVersionUID = 700180386029144608L;

	private final String code = "acesso.incidente-service.error";//NOSONAR
	private final String message = "Erro ao acessar o sistema de incidentes";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR
}
