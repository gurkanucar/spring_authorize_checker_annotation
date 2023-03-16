package com.gucardev.spring_boot_authorize_checker.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Review request model. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStubModel {

  private Long id;

  private Long restaurantId;

  private Long userId;

  private RequestUser requestUser;

  private String content;
}
