package com.solvd.qaprotours.web.dto.validation;

import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lisov Ilya
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateExtension {

    /**
     * Message to be returned in case of validation failed.
     * @return message
     */
    String message() default
            "Invalid filename";

    /**
     * Groups for validation.
     * @return groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload for validation.
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

}
