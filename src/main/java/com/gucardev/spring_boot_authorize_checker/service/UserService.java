package com.gucardev.spring_boot_authorize_checker.service;

import com.gucardev.spring_boot_authorize_checker.aop.AuthorizeCheck;
import com.gucardev.spring_boot_authorize_checker.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  @AuthorizeCheck(field = "id")
  public void updateUserWithFieldValidation(User user) {
    log.info("updateUserWithFieldValidation");
  }

  @AuthorizeCheck(
      field = "wrongFieldName",
      exceptRoles = {"ADMIN"})
  public void updateUserWithRoleValidation(User user) {
    log.info("updateUserWithRoleValidation");
  }

  public void updateUserWithoutValidation(User user) {
    log.info("updateUserWithoutValidation");
  }
}
