package com.bootcamp.designpatterns.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de excecoes para a API
 * 
 * Intercepta e trata excecoes de validacao e outras excecoes comuns,
 * retornando respostas HTTP apropriadas com mensagens de erro claras.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Trata violacoes de validacao de parametros (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Dados de entrada invalidos");
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        errors.put("validationErrors", fieldErrors);
        
        return ResponseEntity.badRequest().body(errors);
    }
    
    /**
     * Trata violacoes de validacao de metodos (@Validated)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Parametros invalidos");
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("message", ex.getMessage());
        
        return ResponseEntity.badRequest().body(errors);
    }
    
    /**
     * Trata parametros obrigatorios ausentes
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMissingParameterException(
            MissingServletRequestParameterException ex) {
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Parametro obrigatorio ausente");
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("message", "Parametro '" + ex.getParameterName() + "' e obrigatorio");
        
        return ResponseEntity.badRequest().body(errors);
    }
    
    /**
     * Trata recursos nao encontrados (404)
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(
            NoResourceFoundException ex) {
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Recurso nao encontrado");
        errors.put("status", HttpStatus.NOT_FOUND.value());
        errors.put("message", "Endpoint nao encontrado: " + ex.getResourcePath());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
    
    /**
     * Trata metodos HTTP nao suportados (405)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Metodo nao permitido");
        errors.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        errors.put("message", "Metodo '" + ex.getMethod() + "' nao e suportado para este endpoint");
        errors.put("supportedMethods", ex.getSupportedMethods());
        
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errors);
    }
    
    /**
     * Trata excecoes gerais nao especificas
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Erro interno do servidor");
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.put("message", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}