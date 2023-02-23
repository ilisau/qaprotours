package com.solvd.qaprotours.web.dto.validation;

import com.solvd.qaprotours.service.property.ImageProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lisov Ilya
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
@RequiredArgsConstructor
public class ImageValidator implements ConstraintValidator<ValidateExtension, Object[]> {

    private final ImageProperties imageProperties;

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {

        if (value[1] == null) {
            return false;
        }

        if (!(value[1] instanceof MultipartFile file)) {
            throw new IllegalArgumentException("Not a multipart file.");
        }

        String extension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        return imageProperties.getExtensions().contains(extension);
    }

}
