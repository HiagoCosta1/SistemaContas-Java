package com.hiago.contas.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
		LocalDateTime timestamp,
		int status,
		String error,
		String message,
		String path,
		List<String> errors
) {
	
	public ErrorResponse (int status, String error, String message, String path) {
		this(LocalDateTime.now(), status, error, message, path, null);
	}
	
	public ErrorResponse(int status, String error, String message, String path, List<String> errors) {
		this(LocalDateTime.now(), status, error, message, path, errors);
	}
}
	
	
