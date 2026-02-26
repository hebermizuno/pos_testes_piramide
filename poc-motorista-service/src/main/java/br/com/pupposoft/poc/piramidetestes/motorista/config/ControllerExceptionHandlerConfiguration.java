package br.com.pupposoft.poc.piramidetestes.motorista.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.ExceptionJson;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.SystemBaseException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandlerConfiguration {

	private static final String EXCEPTION_HANDLER_ERROR_TO_RESOLVE_EXCEPTION = "exceptionHandler.errorToResolveException";
	private static final String ARGUMENT_NOT_VALID = "argumentNotValid";
	private static final String ERROR_TO_RESOLVE_EXCEPTION_HANDLER = "Error to resolve exception handler";

	@ExceptionHandler({ SystemBaseException.class})
	@ResponseBody
	public ResponseEntity<ExceptionJson> sasException(final SystemBaseException e, final HttpServletResponse response) {
		final ExceptionJson exceptionJson = new ExceptionJson(e);
		return new ResponseEntity<>(exceptionJson, new HttpHeaders(), e.getHttpStatus());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ExceptionJson genericError(final Throwable e) {
		log.error(e.getMessage(), e);
		return new ExceptionJson(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}


	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ExceptionJson validationException(final MethodArgumentNotValidException methodArgumentNotValidException) {
		log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
		try {
			final StringBuilder errors = new StringBuilder();

			methodArgumentNotValidException
				.getBindingResult()
				.getFieldErrors()
				.forEach(field -> errors.append(field.getField() + ":" + field.getDefaultMessage()+";"));

			return new ExceptionJson(ARGUMENT_NOT_VALID, errors.toString());

		} catch (Exception exception) {
			log.error(ERROR_TO_RESOLVE_EXCEPTION_HANDLER, exception);
			return new ExceptionJson(EXCEPTION_HANDLER_ERROR_TO_RESOLVE_EXCEPTION, exception.getMessage());
		}
	}


}
