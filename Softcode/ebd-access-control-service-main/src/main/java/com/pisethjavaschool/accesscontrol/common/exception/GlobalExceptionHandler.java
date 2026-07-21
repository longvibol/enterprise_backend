package com.pisethjavaschool.accesscontrol.common.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> business(BusinessException ex, ServerWebExchange exchange) {
        ProblemDetail problem = problem(exchange, ex.getStatus(), ex.getStatus().getReasonPhrase(), ex.getMessage());
        problem.setProperty("errorCode", ex.getErrorCode());
        log.warn("Business error: method={}, path={}, code={}, message={}",
                method(exchange), path(exchange), ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(problem);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ProblemDetail> duplicate(DuplicateKeyException ex, ServerWebExchange exchange) {
        ProblemDetail problem = problem(exchange, HttpStatus.CONFLICT, "Conflict", "Resource already exists");
        problem.setProperty("errorCode", "DUPLICATE_RESOURCE");
        log.warn("Duplicate resource: method={}, path={}, message={}", method(exchange), path(exchange), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ProblemDetail> validation(WebExchangeBindException ex, ServerWebExchange exchange) {
        ProblemDetail problem = problem(exchange, HttpStatus.BAD_REQUEST, "Bad Request", "Validation failed");
        problem.setProperty("errorCode", "VALIDATION_ERROR");

        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            errors.add(Map.of("field", error.getField(), "message", String.valueOf(error.getDefaultMessage())));
        }
        problem.setProperty("errors", errors);

        log.warn("Validation failed: method={}, path={}, count={}", method(exchange), path(exchange), errors.size());
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ProblemDetail> input(ServerWebInputException ex, ServerWebExchange exchange) {
        ProblemDetail problem = problem(exchange, HttpStatus.BAD_REQUEST, "Bad Request", "Invalid request");
        problem.setProperty("errorCode", "INVALID_REQUEST");
        problem.setProperty("reason", ex.getReason());
        problem.setProperty("message", rootCauseMessage(ex));

        log.warn("Invalid request: method={}, path={}, reason={}, message={}",
                method(exchange), path(exchange), ex.getReason(), rootCauseMessage(ex));
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> unknown(Exception ex, ServerWebExchange exchange) {
        ProblemDetail problem = problem(exchange, HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error", "Unexpected server error");
        problem.setProperty("errorCode", "INTERNAL_SERVER_ERROR");
        log.error("Unexpected error: method={}, path={}", method(exchange), path(exchange), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    private String rootCauseMessage(Throwable ex) {
        Throwable current = ex;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current.getMessage();
    }

    private ProblemDetail problem(ServerWebExchange exchange, HttpStatus status, String title, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", path(exchange));
        problem.setProperty("method", method(exchange));
        return problem;
    }

    private String path(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getPath();
    }

    private String method(ServerWebExchange exchange) {
        return exchange.getRequest().getMethod().name();
    }
}
