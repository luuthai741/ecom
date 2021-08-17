package com.test.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFound(Exception ex) {
		return "error/404";
	}
	
	@ExceptionHandler({RuntimeException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String forBidden(Exception ex) {
		return "error/403";
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String serverError(Exception ex) {
		return "error/500";
	}
}
