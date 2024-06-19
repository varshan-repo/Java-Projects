package com.springboot.blog.demospringbootblogrestapi.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.demospringbootblogrestapi.payload.ErrorDetails;

import jakarta.validation.constraints.AssertFalse.List;

// there are 2 approach @ExceptionHandler or by extending ResponseEntityExceptionHandler	

// @ControllerAdvice -> used in GlobalException handler class

// ResponseEntityExceptionHandler -> is used to handle/create custom exception/error thrown by hibernate validator in postman response
// the ResponseEntityExceptionHandler has a in-built method handleMethodArgumentNotValid() for hibernate validator

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handle specific exceptions

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				"Not-Found");

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				"Bad-Request");

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	// global exceptions

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalExceptions(Exception exception, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				"Bad-Request from handleGlobalException Method");

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		/*
			this method is for clear error display to the client based on their expectations
			FieldError.class has getField(), getDefaultMessage etc methods to display the error in client requirement 
			so FieldError.class is casted to error lambda object
			
			getAllErrors() -> returns a list<ObjectErrors>...forEach() is used for iteration
			
		*/
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField(); //  type casting to FieldError.class for .getField() method
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});

		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception, WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), "Un-Authorized");
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

}
