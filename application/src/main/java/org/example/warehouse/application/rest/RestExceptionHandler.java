package org.example.warehouse.application.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.tomcat.util.json.ParseException;
import org.example.warehouse.service.exception.OrderNotModifiableException;
import org.example.warehouse.service.exception.ResourceNotFoundException;
import org.example.warehouse.service.exception.TruckAlreadyExistsException;
import org.example.warehouse.service.exception.UserAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return handleError("Resource not found!", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String msg = "Authentication failed!";
        return handleError(msg, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({OrderNotModifiableException.class})
    public ResponseEntity<Object> handleUnmodifiableOrder(OrderNotModifiableException ex, WebRequest request) {
        return handleError("Order state not changeable", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return handleError("Validation failed!", errors, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({MessagingException.class})
    public ResponseEntity<Object> handleMessagingException(MessagingException ex) {
        return handleError("Email not sent!", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class, org.example.warehouse.service.exception.AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        return handleError("Access denied!", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            JsonProcessingException.class,
            ParseException.class,
//            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            JsonMappingException.class
    })
    public ResponseEntity<Object> handleParsingExceptions(Exception ex, WebRequest request) {
        return handleError("Invalid request body!", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInvalidBodyRequestException(RuntimeException ex, WebRequest request) {
        return handleError("Invalid request body!", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TruckAlreadyExistsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUniqueContraintException(RuntimeException ex, WebRequest request) {
        return handleError("Unique constraint violated!", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleFormatException(IllegalArgumentException ex) {
        String msg = "";
        if (ex instanceof NumberFormatException)
            msg = ", expected number.";
        return handleError("Illegal argument!", ex.getMessage() + msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> handleError(String message, Object reason, HttpStatus httpStatus) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("timestamp",getTimestamp());
        hm.put("status", httpStatus.value());
        hm.put("message",message);
        if (reason != null) hm.put("reason",reason);
        return new ResponseEntity<>(hm, new HttpHeaders(), httpStatus);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Timestamp(System.currentTimeMillis()));
    }
}

