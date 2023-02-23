package com.solvd.qaprotours.web.dto.validation;

import jakarta.validation.Payload;

import java.lang.annotation.*;

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
