package com.czechp.DistillationServiceBackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice()
public class ControllerAdviceComponent {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity badDataRequestExceptionHandler(Exception e, HttpServletRequest request) {
        return createResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity createResponseEntity(String message, HttpStatus status) {
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("message", message);
        return ResponseEntity.status(status).body(responseBody);
    }


}
