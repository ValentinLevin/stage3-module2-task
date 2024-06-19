package com.mjc.school.repository.aop.validation;

import com.mjc.school.repository.exception.CustomRepositoryException;
import com.mjc.school.repository.exception.EntityNullReferenceException;
import com.mjc.school.repository.exception.EntityValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class EntityValidationAspect {
    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Before(value = "@annotation(com.mjc.school.repository.aop.validation.ValidateEntity)")
    public void validateEntity(JoinPoint joinPoint) throws CustomRepositoryException {
        List<String> ignoredFields = getValidationIgnoredFields(joinPoint);
        Object objectForValidation = getObjectForValidate(joinPoint);
        validate(objectForValidation, ignoredFields);
    }

    private List<String> getValidationIgnoredFields(JoinPoint joinPoint) throws CustomRepositoryException {
        String methodName = joinPoint.getSignature().getName();
        try {
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
            ValidateEntity validateEntityAnnotation = method.getAnnotation(ValidateEntity.class);
            String[] ignoredFields = validateEntityAnnotation.ignoreFields();
            return ignoredFields.length == 0 ? Collections.emptyList() : Arrays.asList(ignoredFields);
        } catch (NoSuchMethodException e) {
            throw new CustomRepositoryException(e.getMessage());
        }
    }

    private Object getObjectForValidate(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length > 0) {
            return arguments[0];
        }
        return null;
    }

    private void validate(
            Object object,
            List<String> ignoreFields
    ) throws EntityValidationException, EntityNullReferenceException {
        if (object == null) {
            throw new EntityNullReferenceException();
        }

        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(object)
                        .stream()
                        .filter(cv -> !ignoreFields.contains(cv.getPropertyPath().toString()))
                        .collect(Collectors.toSet());

        if (!constraintViolations.isEmpty()) {
            throw new EntityValidationException(
                    constraintViolations.stream()
                            .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
                            .collect(Collectors.joining(", "))
            );
        }
    }
}
