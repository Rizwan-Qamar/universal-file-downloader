package com.agoda.entities;

public enum BatchStatus {
  UNRECOGNIZED("exception"),
  CREATED("created"),
  COMPLETED("ready for processing");

  private String action;

  public String getAction() {
    return this.action;
  }

  BatchStatus(String action) {
    this.action = action;
  }
}
