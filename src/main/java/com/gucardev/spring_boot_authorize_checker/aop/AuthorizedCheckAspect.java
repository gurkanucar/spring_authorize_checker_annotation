package com.gucardev.spring_boot_authorize_checker.aop;

import com.gucardev.spring_boot_authorize_checker.model.User;
import com.gucardev.spring_boot_authorize_checker.service.AuthService;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizedCheckAspect {
  private final AuthService authService;

  public AuthorizedCheckAspect(AuthService authService) {
    this.authService = authService;
  }

  @Around("@annotation(authorizeCheck)")
  public Object validateUser(ProceedingJoinPoint joinPoint, AuthorizeCheck authorizeCheck)
      throws Throwable {
    Optional<User> authUser = authService.getAuthenticatedUser();
    if (authUser.isEmpty()) {
      return joinPoint.proceed();
    }

    Long authUserId = authUser.get().getId();
    Long userId =
        extractFieldValue(joinPoint.getArgs(), authorizeCheck.field())
            .map(x -> Long.parseLong(x.toString()))
            .orElse(null);

    if (isSelfOrRoleExcepted(authUser.get(), authUserId, userId, authorizeCheck.exceptRoles())) {
      return joinPoint.proceed();
    }

    throw new RuntimeException("Not authorized!");
  }

  private boolean isSelfOrRoleExcepted(
      User authUser, Long authUserId, Long userId, String[] exceptRoles) {
    return authUserId.equals(userId)
        || Stream.of(exceptRoles).anyMatch(role -> authUser.getRole().toString().equals(role));
  }

  private Optional<Object> extractFieldValue(Object[] args, String field) {
    for (Object arg : args) {
      if (arg == null) {
        continue;
      }
      Optional<Object> fieldValue = extractFieldValueFromObject(arg, field);
      if (fieldValue.isPresent()) {
        return fieldValue;
      }
    }
    return Optional.empty();
  }

  private Optional<Object> extractFieldValueFromObject(Object object, String field) {
    try {
      Field declaredField = object.getClass().getDeclaredField(field);
      declaredField.setAccessible(true);
      Object fieldValue = declaredField.get(object);
      return Optional.ofNullable(fieldValue);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // ignore and continue
    }
    return extractNestedObject(object, field)
        .flatMap(
            nestedObject ->
                extractFieldValueFromObject(nestedObject, field.substring(field.indexOf('.') + 1)));
  }

  private Optional<Object> extractNestedObject(Object object, String field) {
    try {
      var declaredField = object.getClass().getDeclaredField(field.split("\\.")[0]);
      declaredField.setAccessible(true);
      Object nestedObject = declaredField.get(object);
      return Optional.ofNullable(nestedObject);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // ignore and continue
    }
    return Optional.empty();
  }
}
