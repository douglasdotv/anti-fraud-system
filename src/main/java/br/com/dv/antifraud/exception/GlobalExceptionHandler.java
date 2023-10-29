package br.com.dv.antifraud.exception;

import br.com.dv.antifraud.dto.error.CustomErrorMessage;
import br.com.dv.antifraud.exception.custom.CannotLockAdminException;
import br.com.dv.antifraud.exception.custom.EntityAlreadyExistsException;
import br.com.dv.antifraud.exception.custom.InvalidRoleException;
import br.com.dv.antifraud.exception.custom.RoleAlreadyAssignedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = new HashMap<>();

    static {
        EXCEPTION_STATUS_MAP.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(EntityAlreadyExistsException.class, HttpStatus.CONFLICT);
        EXCEPTION_STATUS_MAP.put(InvalidRoleException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(RoleAlreadyAssignedException.class, HttpStatus.CONFLICT);
        EXCEPTION_STATUS_MAP.put(CannotLockAdminException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(ConstraintViolationException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            EntityNotFoundException.class,
            EntityAlreadyExistsException.class,
            InvalidRoleException.class,
            RoleAlreadyAssignedException.class,
            CannotLockAdminException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<CustomErrorMessage> handleException(Exception ex, WebRequest request) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException validationException) {
            validationException.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof ConstraintViolationException constraintViolationException) {
            constraintViolationException.getConstraintViolations().forEach(violation -> {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            });
        } else {
            errors.put("error", ex.getMessage());
        }

        CustomErrorMessage errorResponse = new CustomErrorMessage(
                status.value(),
                LocalDateTime.now(),
                request.getDescription(false),
                errors
        );

        return new ResponseEntity<>(errorResponse, status);
    }

}
