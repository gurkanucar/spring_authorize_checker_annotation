package com.gucardev.spring_boot_authorize_checker.service;

import com.gucardev.spring_boot_authorize_checker.model.Role;
import com.gucardev.spring_boot_authorize_checker.model.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private static Long USER_ID = 1L;

  // stub method for getting authenticated user
  // use SecurityContextHolder.getContext().getAuthentication()
  // for real scenarios
  public Optional<User> getAuthenticatedUser() {
    return Optional.ofNullable(User.builder().id(USER_ID).role(Role.ADMIN).build());
  }
}
