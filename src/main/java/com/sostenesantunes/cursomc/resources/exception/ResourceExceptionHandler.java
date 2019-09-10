package com.sostenesantunes.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sostenesantunes.cursomc.services.exceptions.DataIntegrityViolation;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotfoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotfoundException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolation.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolation e, HttpServletRequest request) {
		
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
