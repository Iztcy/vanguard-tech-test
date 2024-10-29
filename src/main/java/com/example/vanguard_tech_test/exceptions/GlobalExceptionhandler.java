package com.example.vanguard_tech_test.exceptions;

import com.example.vanguard_tech_test.response.Response;
import com.example.vanguard_tech_test.response.ResponseFields;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionhandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseFields> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return Response.errorResponse(null, errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseFields> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = ex.getMessage();
        return Response.errorResponse(null, errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseFields> handleDateTimeParseException(DateTimeParseException ex) {
        String errorMessage = ex.getMessage();
        return Response.errorResponse(null, errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportFileExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseFields> handleImportFileExistException(ImportFileExistException ex) {
        String errorMessage = ex.getMessage();
        return Response.errorResponse(null, errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseFields> otherException(Exception ex) {
        String errorMessage = ex.getMessage();
        return Response.errorResponse(null, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
