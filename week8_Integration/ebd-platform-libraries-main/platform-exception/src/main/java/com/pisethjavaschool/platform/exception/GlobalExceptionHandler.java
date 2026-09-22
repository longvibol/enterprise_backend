package com.pisethjavaschool.platform.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.*;
import java.time.Instant;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ProblemDetail> business(BusinessException ex, ServerWebExchange exchange) {
		ProblemDetail p = problem(exchange, ex.getStatus(), ex.getStatus().getReasonPhrase(), ex.getMessage());
		p.setProperty("errorCode", ex.getErrorCode());
		log.warn("Business error: method={}, path={}, code={}, message={}", method(exchange), path(exchange),
				ex.getErrorCode(), ex.getMessage());
		return ResponseEntity.status(ex.getStatus()).body(p);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ProblemDetail> duplicate(DuplicateKeyException ex, ServerWebExchange exchange) {
		ProblemDetail p = problem(exchange, HttpStatus.CONFLICT, "Conflict", "Resource already exists");
		p.setProperty("errorCode", "DUPLICATE_RESOURCE");
		log.warn("Duplicate resource: method={}, path={}, message={}", method(exchange), path(exchange),
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(p);
	}

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<ProblemDetail> validation(WebExchangeBindException ex, ServerWebExchange exchange) {
		ProblemDetail p = problem(exchange, HttpStatus.BAD_REQUEST, "Bad Request", "Validation failed");
		p.setProperty("errorCode", "VALIDATION_ERROR");
		List<Map<String, String>> errors = new ArrayList<>();
		for (FieldError e : ex.getFieldErrors()) {
			errors.add(Map.of("field", e.getField(), "message", String.valueOf(e.getDefaultMessage())));
		}
		p.setProperty("errors", errors);
		log.warn("Validation failed: method={}, path={}, count={}", method(exchange), path(exchange), errors.size());
		return ResponseEntity.badRequest().body(p);
	}
	
	@ExceptionHandler(ServerWebInputException.class)
	public ResponseEntity<ProblemDetail> input(ServerWebInputException ex, ServerWebExchange exchange) {
	    String realMessage = rootCauseMessage(ex);

	    ProblemDetail p = problem(exchange, HttpStatus.BAD_REQUEST, "Bad Request", "Invalid request");
	    p.setProperty("errorCode", "INVALID_REQUEST");
	    p.setProperty("reason", ex.getReason());
	    p.setProperty("message", realMessage);

	    log.warn(
	            "Invalid request: method={}, path={}, reason={}, message={}",
	            method(exchange),
	            path(exchange),
	            ex.getReason(),
	            realMessage
	    );

	    return ResponseEntity.badRequest().body(p);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetail> unknown(Exception ex, ServerWebExchange exchange) {
		ProblemDetail p = problem(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
				"Unexpected server error");
		p.setProperty("errorCode", "INTERNAL_SERVER_ERROR");
		log.error("Unexpected error: method={}, path={}", method(exchange), path(exchange), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(p);
	}
	
	private String rootCauseMessage(Throwable ex) {
	    Throwable current = ex;
	    while (current.getCause() != null) {
	        current = current.getCause();
	    }
	    return current.getMessage();
	}

	private ProblemDetail problem(ServerWebExchange e, HttpStatus s, String title, String detail) {
		ProblemDetail p = ProblemDetail.forStatusAndDetail(s, detail);
		p.setTitle(title);
		p.setProperty("timestamp", Instant.now());
		p.setProperty("path", path(e));
		p.setProperty("method", method(e));
		return p;
	}

	private String path(ServerWebExchange e) {
		return e.getRequest().getURI().getPath();
	}

	private String method(ServerWebExchange e) {
		return e.getRequest().getMethod().name();
	}
}
