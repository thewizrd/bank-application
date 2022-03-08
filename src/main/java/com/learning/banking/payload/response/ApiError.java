package com.learning.banking.payload.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

/**
 * ApiError
 *
 * @author bryan
 * @date Feb 16, 2022-10:16:22 AM
 */
@Data
public class ApiError {
	private HttpStatus status;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<ApiSubError> subErrors; // holds the validational errors
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	// every field validation in SubError
	// create a subError and add it to the list
	private void addSubError(ApiSubError subError) {
		if (subErrors == null) {
			subErrors = new ArrayList<>();
		}
		
		subErrors.add(subError);
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new ApiValidationError(object, field, rejectedValue, message));
	}
	
	private void addValidationError(String object, String message) {
		addSubError(new ApiValidationError(object, message));
	}
	
	private void addValidationError(FieldError error) {
		this.addValidationError(error.getObjectName(),
				error.getField(),
				error.getRejectedValue(),
				error.getDefaultMessage());
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach((e) -> {
			this.addValidationError(e);
		});
	}
	
	public void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
	
	public void addValidationObjectErrors(List<ObjectError> objectErrors) {
		objectErrors.forEach(e -> {
			if (e instanceof FieldError) {
				addValidationError((FieldError) e);
			} else {
				addValidationError(e);
			}
		});
	}
	
	public void addValidationError(ConstraintViolation<?> cv) {
		String field;
		if (cv.getPropertyPath() instanceof PathImpl) {
			field = ((PathImpl)cv.getPropertyPath()).getLeafNode().asString();
		} else {
			field = cv.getPropertyPath().toString();
		}
		
		this.addValidationError(
				cv.getRootBeanClass().getName(),
				field,
				cv.getInvalidValue(),
				cv.getMessage());
	}
	
	public void addValidationErrors(Set<ConstraintViolation<?>> violations) {
		violations.forEach(cv -> {
			addValidationError(cv);
		});
	}
}
