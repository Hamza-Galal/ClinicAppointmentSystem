package com.ClinicSystem.ClinicAppointmentSystem.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.ClinicSystem.ClinicAppointmentSystem.Exception.DoctorNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateEmailException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateLicenseException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.InvalidAppointmentStatusException;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage()));

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
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(
            PatientNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(
            DuplicateEmailException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDoctorNotFoundException(
            DoctorNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateLicenseException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateLicenseException(
            DuplicateLicenseException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    @ExceptionHandler(SpecializationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSpecializationNotFoundException(
            SpecializationNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
     @ExceptionHandler(DuplicateSpecializationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSpecializationException(
            DuplicateSpecializationException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

        @ExceptionHandler(InvalidScheduleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidScheduleException(InvalidScheduleException ex,
            HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ScheduleConflictException.class)
    public ResponseEntity<ErrorResponse> handleScheduleConflictException(ScheduleConflictException ex,
            HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFoundException(AppointmentNotFoundException ex , HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler (AppointmentConflictException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentConflictException(AppointmentConflictException ex , HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler (OutsideWorkingHoursException.class)
    public ResponseEntity<ErrorResponse> handleOutsideWorkingHoursException(OutsideWorkingHoursException ex , HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler (DuplicateScheduleException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateScheduleException(DuplicateScheduleException ex , HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }
    @ExceptionHandler(InvalidAppointmentStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAppointmentStatusException(
            InvalidAppointmentStatusException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMedicalRecordNotFoundException(
            MedicalRecordNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(DuplicateMedicalRecordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMedicalRecordException(
            DuplicateMedicalRecordException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(409);
        response.setError("Conflict Error");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(InvalidMedicalRecordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMedicalRecordException(
            InvalidMedicalRecordException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler (UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex ,
         HttpServletRequest request){
            ErrorResponse response = new ErrorResponse();
            response.setTimeStamp(LocalDateTime.now());
            response.setStatus(409);
            response.setError("Conflict Error");
            response.setMessage(ex.getMessage());
            response.setPath(request.getRequestURI());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
         }
    @ExceptionHandler (InvalidCredintialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredintialsException(InvalidCredintialsException ex ,
    HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(401);
        response.setError("Unauthorized");
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    @ExceptionHandler (AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex ,
         HttpServletRequest request){
            ErrorResponse response = new ErrorResponse();
            response.setTimeStamp(LocalDateTime.now());
            response.setStatus(403);
            response.setError("Forbidden");
            response.setMessage(ex.getMessage());
            response.setPath(request.getRequestURI());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
         }
}

