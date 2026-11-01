package com.gucardev.spring_boot_authorize_checker.aop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.gucardev.spring_boot_authorize_checker.helper.RequestStubModel;
import com.gucardev.spring_boot_authorize_checker.helper.RequestUser;
import com.gucardev.spring_boot_authorize_checker.model.Role;
import com.gucardev.spring_boot_authorize_checker.model.User;
import com.gucardev.spring_boot_authorize_checker.service.AuthService;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AuthorizedCheckAspectTest {

  @Mock
  private AuthService authService;
  @InjectMocks
  private AuthorizedCheckAspect authorizedCheckAspect;

  @Mock private ProceedingJoinPoint joinPoint;

  @Test
  void shouldAllowAccessForAuthenticatedAndSelfUser() throws Throwable {

    var USER_ID = 100L;

    // Arrange
    AuthorizeCheck authorizeCheck = mock(AuthorizeCheck.class);
    User user = User.builder().id(USER_ID).role(Role.USER).build();
    setupAuthorizeCheckExceptRolesAndFieldAndGetArgsAnd1MoreStubs(user, authorizeCheck, new String[] {}, "userId", new Object[] { RequestStubModel.builder().id(1L).restaurantId(1L).userId(USER_ID).build() });

    // Act
    Object result = authorizedCheckAspect.validateUser(joinPoint, authorizeCheck);

    // Assert
    assertEquals(joinPoint.proceed(), result);
  }

  @Test
  void shouldAllowAccessForAuthenticatedAndSelfUserWithNestedField() throws Throwable {

    var USER_ID = 100L;

    // Arrange
    AuthorizeCheck authorizeCheck = mock(AuthorizeCheck.class);
    User user = User.builder().id(USER_ID).role(Role.USER).build();
    setupAuthorizeCheckExceptRolesAndFieldAndGetArgsAnd1MoreStubs(user, authorizeCheck, new String[] {}, "requestUser.userIdField", new Object[] { RequestStubModel.builder().id(1L).restaurantId(1L).requestUser(RequestUser.builder().userIdField(USER_ID).build()).build() });

    // Act
    Object result = authorizedCheckAspect.validateUser(joinPoint, authorizeCheck);

    // Assert
    assertEquals(joinPoint.proceed(), result);
  }

  @Test
  void shouldThrowExceptionForDifferentUserId() throws Throwable {

    var USER_ID = 100L;

    // Arrange
    AuthorizeCheck authorizeCheck = mock(AuthorizeCheck.class);
    User user = User.builder().id(USER_ID).role(Role.USER).build();
    setupAuthorizeCheckExceptRolesAndFieldAndGetArgsAnd1MoreStubs(user, authorizeCheck, new String[] {}, "userId", new Object[] { RequestStubModel.builder().id(1L).restaurantId(1L).userId(1L).build() });

    assertThrows(
        RuntimeException.class,
        () -> {
          authorizedCheckAspect.validateUser(joinPoint, authorizeCheck);
        });
  }

  @Test
  void shouldAllowAccessForExceptRolesWithDifferentUserId() throws Throwable {

    var USER_ID = 100L;
    var USER_ID2 = 10L;

    // Arrange
    AuthorizeCheck authorizeCheck = mock(AuthorizeCheck.class);
    User user = User.builder().id(USER_ID).role(Role.ADMIN).build();
    setupAuthorizeCheckExceptRolesAndFieldAndGetArgsAnd1MoreStubs(user, authorizeCheck, new String[] { "ADMIN" }, "userId", new Object[] { RequestStubModel.builder().id(1L).restaurantId(1L).userId(USER_ID2).build() });

    // Act
    Object result = authorizedCheckAspect.validateUser(joinPoint, authorizeCheck);

    // Assert
    assertEquals(joinPoint.proceed(), result);
  }

  @Test
  void shouldThrowExceptionForExceptRolesWithDifferentUserId() throws Throwable {

    var USER_ID = 100L;
    var USER_ID2 = 10L;

    // Arrange
    AuthorizeCheck authorizeCheck = mock(AuthorizeCheck.class);
    User user = User.builder().id(USER_ID).role(Role.USER).build();
    setupAuthorizeCheckExceptRolesAndFieldAndGetArgsAnd1MoreStubs(user, authorizeCheck, new String[] { "ADMIN" }, "userId", new Object[] { RequestStubModel.builder().id(1L).restaurantId(1L).userId(USER_ID2).build() });

    // Assert
    assertThrows(
        RuntimeException.class,
        () -> {
          authorizedCheckAspect.validateUser(joinPoint, authorizeCheck);
        });
  }

  private void setupAuthorizeCheckExceptRolesAndFieldAndGetArgsAnd1MoreStubs(User user, AuthorizeCheck authorizeCheck, String[] exceptRolesReturnValue, String fieldReturnValue, Object[] getArgsReturnValue) throws Throwable {
    when(authService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    when(joinPoint.getArgs()).thenReturn(getArgsReturnValue);
    when(authorizeCheck.field()).thenReturn(fieldReturnValue);
    when(authorizeCheck.exceptRoles()).thenReturn(exceptRolesReturnValue);
  }
}
