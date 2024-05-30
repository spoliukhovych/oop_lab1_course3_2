package com.airportmanagementbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ErrorMessage {

  private String errorId;
  private String message;

}