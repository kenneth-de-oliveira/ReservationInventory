package io.bookwise.framework.errors;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Error> handleOthersExceptions(Exception ex) {
		 Error response = getError(GenericErrorsEnum.ERROR_GENERIC,HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Error> badRequestException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Error> bindRequestException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Invalid request");
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodNotAllowedException.class, HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<Error> methodNotAllowedException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler({ UnsupportedOperationException.class })
	@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
	public ResponseEntity<Error> methodNotImplementedException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.METHOD_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED.value(),ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler({ HttpServerErrorException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Error> serverErrorException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(),ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ MethodNotFoundException.class, NoHandlerFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Error> notFoundException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.NOT_FOUND, HttpStatus.NOT_FOUND.value(),ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ HttpClientErrorException.class })
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ResponseEntity<Error> forbiddenException(Exception ex) {
		 Error response = getError(GenericErrorsEnum.FORBIDDEN, HttpStatus.FORBIDDEN.value(),ex.getMessage());
		log.error(response.toString(), ex.getCause());
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	private Error getError(GenericErrorsEnum error, int statusCode, String exceptionMessage) {
		String info = exceptionMessage != null ? error.getInfo().concat(": " + exceptionMessage) : error.getInfo();
		return new Error(error.getCode(), error.getReason(), info, statusCode);
	}

}