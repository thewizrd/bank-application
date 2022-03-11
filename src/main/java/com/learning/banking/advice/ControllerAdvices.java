package com.learning.banking.advice;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.learning.banking.exceptions.IdNotFoundException;
import com.learning.banking.exceptions.InsufficientFundsException;
import com.learning.banking.exceptions.NoDataFoundException;
import com.learning.banking.exceptions.NoRecordsFoundException;
import com.learning.banking.exceptions.TransferException;
import com.learning.banking.exceptions.UserNameAlreadyExistsException;
import com.learning.banking.payload.response.ApiError;

/**
 * ControllerAdvices
 *
 * @author bryan
 * @date Mar 10, 2022-3:34:42 PM
 */
@RestControllerAdvice
public class ControllerAdvices extends ResponseEntityExceptionHandler {
	/* Custom Exceptions */
	@ExceptionHandler(NoRecordsFoundException.class)
	public ResponseEntity<?> handleNoRecordsFoundException(NoRecordsFoundException e) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(e.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<?> handleInsufficientFundsException(InsufficientFundsException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(UserNameAlreadyExistsException.class) // this is reponsible for handing NameAlreadyExistsException
	public ResponseEntity<?> nameAlreadyExistsException(UserNameAlreadyExistsException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "username already exists", e);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> NoDataFoundException(NoDataFoundException e) {
		// HttpStatus.NO_content is for delete
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), e);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundException(IdNotFoundException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "role id not found exception", e);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(TransferException.class)
	public ResponseEntity<?> trandferException(TransferException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		return buildResponseEntity(apiError);
	}

	/* Validation Exceptions */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(e.getConstraintViolations());

		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// @Valid - validation failed
		ApiError apiError = new ApiError(status);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getFieldErrors());
		apiError.addValidationObjectErrors(ex.getBindingResult().getGlobalErrors());

		return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(status);
		apiError.setMessage(ex.getMessage());
		apiError.setDebugMessage(ex.getRequiredType().getName());

		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError error) {
		return ResponseEntity.status(error.getStatus()).body(error);
	}
}
