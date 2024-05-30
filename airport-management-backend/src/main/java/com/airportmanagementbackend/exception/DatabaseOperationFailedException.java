package com.airportmanagementbackend.exception;

public class DatabaseOperationFailedException extends RuntimeException {

  public DatabaseOperationFailedException(String message) {
    super(message);
  }

}
