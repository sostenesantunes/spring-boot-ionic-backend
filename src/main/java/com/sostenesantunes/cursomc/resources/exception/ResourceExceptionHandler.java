package com.sostenesantunes.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.sostenesantunes.cursomc.services.exceptions.AuthorizationException;
import com.sostenesantunes.cursomc.services.exceptions.DataIntegrityViolation;
import com.sostenesantunes.cursomc.services.exceptions.FileException;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotfoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotfoundException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não Encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolation.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolation e, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de Dados", e.getMessage(), request.getRequestURI());		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ValidationError error =  new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Error de validação", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			error.addError(x.getField(), x.getDefaultMessage());	
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(AuthorizationException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.valueOf(e.getErrorCode());
		StandardError error = new StandardError(System.currentTimeMillis(), status.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonCliente(AuthorizationException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro S3", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}



}
