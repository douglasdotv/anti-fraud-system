package br.com.dv.antifraud.exception;

import br.com.dv.antifraud.dto.CustomErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        CustomErrorMessage errorResponse = new CustomErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleEntityNotFoundException(
            EntityNotFoundException ex,
            WebRequest request
    ) {
        CustomErrorMessage errorResponse = new CustomErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                Collections.singletonMap("error", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomErrorMessage> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            WebRequest request
    ) {
        CustomErrorMessage errorResponse = new CustomErrorMessage(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                Collections.singletonMap("error", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<CustomErrorMessage> handleInvalidRoleException(
            InvalidRoleException ex,
            WebRequest request
    ) {
        CustomErrorMessage errorResponse = new CustomErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                Collections.singletonMap("error", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RoleAlreadyAssignedException.class)
    public ResponseEntity<CustomErrorMessage> handleRoleAlreadyAssignedException(
            RoleAlreadyAssignedException ex,
            WebRequest request
    ) {
        CustomErrorMessage errorResponse = new CustomErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                Collections.singletonMap("error", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CannotLockAdminException.class)
    public ResponseEntity<CustomErrorMessage> handleCannotLockAdminException(
            CannotLockAdminException ex,
            WebRequest request
    ) {
        CustomErrorMessage errorResponse = new CustomErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                Collections.singletonMap("error", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
