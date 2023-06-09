package com.example.demo.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleRecordNotFoundException(RecordNotFoundException ex) {
        // Here you could log the exception message and return a view name
        // to show an error page to the user
        return "error";
    }
}

