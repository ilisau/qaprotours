package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.exception.ImageUploadException;
import com.solvd.qaprotours.domain.exception.InvalidTokenException;
import com.solvd.qaprotours.domain.exception.NoFreePlacesException;
import com.solvd.qaprotours.domain.exception.PasswordMismatchException;
import com.solvd.qaprotours.domain.exception.ResourceAlreadyExistsException;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.exception.ServiceNotAvailableException;
import com.solvd.qaprotours.domain.exception.TourAlreadyStartedException;
import com.solvd.qaprotours.domain.exception.UserClientException;
import com.solvd.qaprotours.web.dto.ErrorDto;
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

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(ResourceDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleResourceDoesNotExistException(
            final ResourceDoesNotExistException ex
    ) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleResourceAlreadyExistsException(
            final ResourceAlreadyExistsException ex
    ) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handlePasswordMismatchException(
            final PasswordMismatchException ex
    ) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(NoFreePlacesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleNoFreePlacesException(
            final NoFreePlacesException ex
    ) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleTokenExpiredException(
            final InvalidTokenException ex
    ) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleAuthException(final AuthException ex) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param ex ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(TourAlreadyStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleTourAlreadyStartedException(
            final TourAlreadyStartedException ex
    ) {
        return new ErrorDto(ex.getMessage());
    }

    /**
     * Handles ConstraintViolationException.
     * @param e ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        ErrorDto exceptionBody = new ErrorDto("Validation failed");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setDetails(errors.stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    /**
     * Handles ConstraintViolationException.
     * @param e ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleConstraintViolationException(
            final ConstraintViolationException e
    ) {
        ErrorDto exceptionBody = new ErrorDto("Validation failed");
        exceptionBody.setDetails(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(v ->
                                v.getPropertyPath().toString(),
                        ConstraintViolation::getMessage)));
        return exceptionBody;
    }

    /**
     * Handles ConstraintViolationException.
     * @param e ConstraintViolationException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBindException(final BindException e) {
        ErrorDto exceptionBody = new ErrorDto("Validation failed");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setDetails(errors.stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    /**
     * Handles AccessDeniedException.
     * @param e AccessDeniedException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleAccessDeniedException(final AccessDeniedException e) {
        return new ErrorDto("Access denied");
    }

    /**
     * Handles ImageUploadException.
     * @param e ImageUploadException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleImageUploadException(final ImageUploadException e) {
        return new ErrorDto(e.getMessage());
    }

    /**
     * Handles ImageUploadException.
     * @param e ImageUploadException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleBadCredentialsException(
            final BadCredentialsException e
    ) {
        return new ErrorDto("Bad credentials.");
    }

    /**
     * Handles ImageUploadException.
     * @param e ImageUploadException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(ServiceNotAvailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDto handleServiceNotAvailableException(
            final ServiceNotAvailableException e
    ) {
        return new ErrorDto("Service is not available. Try again.");
    }

    /**
     * Handles ImageUploadException.
     * @param e ImageUploadException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler(UserClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMicroserviceException(final UserClientException e) {
        return new ErrorDto(e.getMessage(), e.getDetails());
    }

    /**
     * Handles ImageUploadException.
     * @param e ImageUploadException object.
     * @return ErrorDto object.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(final Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        return new ErrorDto("Internal server error");
    }

}
