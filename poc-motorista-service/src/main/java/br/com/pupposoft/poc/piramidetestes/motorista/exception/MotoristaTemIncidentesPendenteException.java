package br.com.pupposoft.poc.piramidetestes.motorista.exception;

import lombok.Getter;

@Getter
public class MotoristaTemIncidentesPendenteException extends SystemBaseException {
	private static final long serialVersionUID = -6293706502240259730L;

	private final String code = "motoristaComInicentePendente";//NOSONAR
	private final String message = "Motorista possui incidentes";//NOSONAR
	private final Integer httpStatus = 422;//NOSONAR
}
