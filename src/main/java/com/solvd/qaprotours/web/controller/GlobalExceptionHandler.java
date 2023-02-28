package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.exception.*;
import com.solvd.qaprotours.web.dto.ErrorDto;
import jakarta.mail.SendFailedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleResourceDoesNotExistException(ResourceDoesNotExistException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handlePasswordMismatchException(PasswordMismatchException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(NoFreePlacesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleNoFreePlacesException(NoFreePlacesException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleTokenExpiredException(InvalidTokenException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleAuthException(AuthException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(TourAlreadyStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleTourAlreadyStartedException(TourAlreadyStartedException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorDto exceptionBody = new ErrorDto("Validation failed");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setDetails(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
        ErrorDto exceptionBody = new ErrorDto("Validation failed");
        exceptionBody.setDetails(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBindException(BindException e) {
        ErrorDto exceptionBody = new ErrorDto("Validation failed");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setDetails(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(SendFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleSendFailedException(SendFailedException e) {
        return new ErrorDto("Error while sending email");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleAccessDeniedException(AccessDeniedException e) {
        return new ErrorDto("Access denied");
    }

    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleImageUploadException(ImageUploadException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleBadCredentials(BadCredentialsException e) {
        return new ErrorDto("Bad credentials.");
    }

    @ExceptionHandler(ServiceNotAvailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDto handleServiceNotAvailableException(ServiceNotAvailableException e) {
        return new ErrorDto("Service is not available. Try again.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        return new ErrorDto("Internal server error");
    }

}
