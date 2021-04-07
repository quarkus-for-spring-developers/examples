package org.acme.rest;

import org.acme.domain.CustomRuntimeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
	@ExceptionHandler(CustomRuntimeException.class)
	public ResponseEntity<CustomError> handleCustomRuntimeException(CustomRuntimeException cre) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.header("X-CUSTOM-ERROR", "500")
			.body(new CustomError(500, cre.getMessage()));
	}
}
