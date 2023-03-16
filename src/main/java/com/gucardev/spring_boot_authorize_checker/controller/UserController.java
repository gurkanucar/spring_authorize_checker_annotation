package com.gucardev.spring_boot_authorize_checker.controller;

import com.gucardev.spring_boot_authorize_checker.model.User;
import com.gucardev.spring_boot_authorize_checker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PutMapping
  ResponseEntity<String> updateUserWithoutValidation(@RequestBody User user) {
    userService.updateUserWithoutValidation(user);
    return ResponseEntity.ok("success");
  }

  @PutMapping("/field")
  ResponseEntity<String> updateUserWithFieldValidation(@RequestBody User user) {
    userService.updateUserWithFieldValidation(user);
    return ResponseEntity.ok("success");
  }

  @PutMapping("/field-role")
  ResponseEntity<String> updateUserWithRoleValidation(@RequestBody User user) {
    userService.updateUserWithRoleValidation(user);
    return ResponseEntity.ok("success");
  }
}
