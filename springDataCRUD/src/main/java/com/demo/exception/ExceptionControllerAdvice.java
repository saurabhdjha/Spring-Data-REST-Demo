package com.demo.exception;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	Environment environment;
	
	private static final Log LOGGER=LogFactory.getLog(ExceptionControllerAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception)
	{
		ErrorInfo error=new ErrorInfo();
		error.setErrorMessage(environment.getProperty("GENERAL.EXECPTION_OCCURED"));
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTimestamp(LocalDateTime.now());
		
		LOGGER.error(error);
		
		return new ResponseEntity<ErrorInfo>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ErrorInfo> customerExceptionHandler(CustomerException exception)
	{
		ErrorInfo error=new ErrorInfo();
		error.setErrorMessage(environment.getProperty(exception.getMessage()));
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(LocalDateTime.now());
		
		LOGGER.error(error);
		
		return new ResponseEntity<ErrorInfo>(error,HttpStatus.NOT_FOUND);
	}
}
