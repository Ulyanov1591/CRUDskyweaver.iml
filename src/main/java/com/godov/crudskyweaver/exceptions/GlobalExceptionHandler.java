package com.godov.crudskyweaver.exceptions;

import com.godov.crudskyweaver.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchMatchFoundException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchMatchFoundException(RuntimeException exception, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
