package com.mjc.school.service.aop.validation;

import com.mjc.school.service.exception.CustomServiceException;
import com.mjc.school.service.exception.DTOValidationServiceException;
import com.mjc.school.service.exception.RequestNullReferenceException;
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

@Component
@Aspect
public class DTOValidationAspect {
    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Before("@annotation(ValidateDTO)")
    public void validateDTO(JoinPoint joinPoint) throws CustomServiceException {
        List<String> ignoredFields = getValidationIgnoredFields(joinPoint);
        Object objectForValidation = getObjectForValidation(joinPoint);
        validate(objectForValidation, ignoredFields);
    }

    private List<String> getValidationIgnoredFields(JoinPoint joinPoint) throws CustomServiceException {
        String methodName = joinPoint.getSignature().getName();
        try {
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
            ValidateDTO validateDTOAnnotation = method.getAnnotation(ValidateDTO.class);
            String[] ignoredFields = validateDTOAnnotation.ignoreFields();
            return ignoredFields.length == 0 ? Collections.emptyList() : Arrays.asList(ignoredFields);
        } catch (NoSuchMethodException e) {
            throw new CustomServiceException(e.getMessage());
        }
    }

    private Object getObjectForValidation(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            return args[0];
        }
        return null;
    }


    private void validate(
            Object object,
            List<String> ignoreFields
    ) throws DTOValidationServiceException, RequestNullReferenceException {
        if (object == null) {
            throw new RequestNullReferenceException();
        }

        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(object)
                        .stream()
                        .filter(cv -> !ignoreFields.contains(cv.getPropertyPath().toString()))
                        .collect(Collectors.toSet());

        if (!constraintViolations.isEmpty()) {
            throw new DTOValidationServiceException(
                    constraintViolations.stream()
                            .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
                            .collect(Collectors.joining(", "))
            );
        }
    }
}
