package com.godov.crudskyweaver.controllerAdvice;

import com.godov.crudskyweaver.dto.ErrorDTO;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchMatchFoundException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchMatchFoundException(RuntimeException exception) {
        final ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                exception.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(BindException exception){
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(violation -> new Violation(violation.getField(), violation.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseEntity<>(new ValidationErrorResponse(violations), HttpStatus.BAD_REQUEST);
    }

}
