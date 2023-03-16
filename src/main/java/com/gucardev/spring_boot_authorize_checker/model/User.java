package com.gucardev.spring_boot_authorize_checker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

  private Long id;

  private String username;
  private String name;
  private String surname;

  private String mail;
  private Role role;
}
