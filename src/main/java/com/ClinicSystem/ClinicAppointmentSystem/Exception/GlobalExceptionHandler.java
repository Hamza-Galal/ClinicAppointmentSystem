package com.ClinicSystem.ClinicAppointmentSystem.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValdiationException(MethodArgumentNotValidException ex ,
    HttpServletRequest request){
    Map<String,String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error->fieldErrors.put(error.getField(), error.getDefaultMessage()));
    ErrorResponse response = new ErrorResponse();
    response.setTimeStamp(LocalDateTime.now());
    response.setStatus(400);
    response.setError("Bad Request");
    response.setMessage("Validation Failed");
    response.setPath(request.getRequestURI());
    response.setFieldError(fieldErrors);
    return ResponseEntity.badRequest().body(response);
}

@ExceptionHandler(PatientNotFoundException.class)
public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex , 
    HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}

@ExceptionHandler(DuplicateEmailException.class)
public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex,
     HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}

}
