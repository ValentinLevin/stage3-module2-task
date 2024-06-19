package com.mjc.school.service.aop.validation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ValidateDTO {
    String[] ignoreFields() default {};
}
