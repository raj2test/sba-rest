package com.fsd.sba.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionResponseHandler {
	
	private final Logger log = LoggerFactory.getLogger(ExceptionResponseHandler.class);
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(SbaException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> handleSbaException(final SbaException exception,
			final HttpServletRequest request, Locale locale) {

		String errorMessage = messageSource.getMessage(exception.getCode().getCode(), null, locale);
		ExceptionResponse error = new ExceptionResponse(errorMessage);
		
		return new ResponseEntity<>(error, exception.getStatus());
	}
	

}
