package com.gucardev.spring_boot_authorize_checker.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public
class RequestUser {

  private Long userIdField;
}
