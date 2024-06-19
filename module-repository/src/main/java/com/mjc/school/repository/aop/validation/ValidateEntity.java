package com.mjc.school.repository.aop.validation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ValidateEntity {
    String[] ignoreFields() default {};
}
