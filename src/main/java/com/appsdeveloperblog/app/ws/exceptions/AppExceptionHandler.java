package com.appsdeveloperblog.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {

	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		String errorMessagedesc=ex.getLocalizedMessage();
		if(errorMessagedesc==null) errorMessagedesc = ex.toString();
				ErrorMessage errorMessage =new  ErrorMessage(new Date(),ex.getLocalizedMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(value= {NullPointerException.class,UserServiceException.class})
	public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request) {
		String errorMessagedesc=ex.getLocalizedMessage();
		if(errorMessagedesc==null) errorMessagedesc = ex.toString();
				ErrorMessage errorMessage =new  ErrorMessage(new Date(),ex.getLocalizedMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
	}
	
	/**@ExceptionHandler(value= {UserServiceException.class})
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
		String errorMessagedesc=ex.getMessage();
		if(errorMessagedesc==null) errorMessagedesc = ex.toString();
				ErrorMessage errorMessage =new  ErrorMessage(new Date(),ex.getLocalizedMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
	}**/
}
