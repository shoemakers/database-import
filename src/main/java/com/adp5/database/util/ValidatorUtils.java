package com.adp5.database.util;

import com.adp5.database.core.exception.DataValidateException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import javax.validation.Validator;
import javax.validation.groups.Default;

@Slf4j
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     */
    public static void validateEntity(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, Default.class);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new DataValidateException(msg.toString());
        }
    }
}
