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

    String message() default
            "Invalid filename";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
