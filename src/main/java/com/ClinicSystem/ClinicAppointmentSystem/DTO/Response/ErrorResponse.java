package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
 private LocalDateTime timeStamp;
 private int status;
 private String error;
 private String message;
 private String path;
 private Map<String,String> fieldError;
 
 public ErrorResponse() {
 }

 public ErrorResponse(LocalDateTime timeStamp, int status, String error, String message, String path) {
    this.timeStamp = timeStamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
 }

 public ErrorResponse(LocalDateTime timeStamp, int status, String error, String message, String path,
        Map<String, String> fieldError) {
    this.timeStamp = timeStamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
    this.fieldError = fieldError;
 }

 
}
