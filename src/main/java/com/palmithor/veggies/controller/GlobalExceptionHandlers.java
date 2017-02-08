package com.palmithor.veggies.controller;

import com.palmithor.veggies.controller.exception.ResourceNotFoundException;
import com.palmithor.veggies.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * A global exception handler to take care of all exceptions.
 * <p>
 * Sets the correct status code and constructs a response
 *
 * @author palmithor
 * @since 8.2.2017.
 */
@ControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorModel> handleResourceNotFoundException(final ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel("Resource not found"));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorModel> handleConstraintViolationException(final ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        return ResponseEntity.badRequest().body(new ErrorModel(violations.iterator().next().getMessage()));
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorModel> handleUnexpectedException(final Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorModel("An unexpected error occurred"));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorModel> handleRequestBodyMissing(final HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorModel("Required request body is missing"));
    }
}
