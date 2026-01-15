package com.ssnack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex){
    return build(HttpStatus.NOT_FOUND, ex.getMessage());
  }
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex){
    return build(HttpStatus.BAD_REQUEST, ex.getMessage());
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleValidation(MethodArgumentNotValidException ex){
    Map<String, Object> body = baseBody(HttpStatus.BAD_REQUEST, "Validation failed");
    body.put("details: ", ex.getBindingResult().toString());
    return body;
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex){
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
  }
  private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message){
    Map<String, Object> body = baseBody(status, message);
    return ResponseEntity.status(status).body(body);
  }
  private Map<String, Object> baseBody(HttpStatus status, String message){
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", Instant.now().toString());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    return body;
  }
}
