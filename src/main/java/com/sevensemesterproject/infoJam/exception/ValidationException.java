package com.sevensemesterproject.infoJam.exception;

import org.hibernate.service.spi.ServiceException;

public class ValidationException extends ServiceException {
	
	public ValidationException(String message) {
		super(message);
	
	}
}
