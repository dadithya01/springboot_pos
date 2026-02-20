package edu.example.pos_backend.exception;

import edu.example.pos_backend.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> generalExceptionHandler(Exception e){
       return new ResponseEntity<>(new APIResponse<>(
               HttpStatus.INTERNAL_SERVER_ERROR.value(),
               "internal server error",
               null
       ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<APIResponse<String>> nullExceptionHandler(NullPointerException e){
        return new ResponseEntity<>(new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "null values are not allowed",
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(
                new APIResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "validation failed",
                        errors
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse<String>> badRequestExceptionHandler(BadRequestException e) {
       return new ResponseEntity<>(new APIResponse<>(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse<String>> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return new ResponseEntity<>(new APIResponse<>(404, e.getMessage(), null), HttpStatus.NOT_FOUND);
    }

}
