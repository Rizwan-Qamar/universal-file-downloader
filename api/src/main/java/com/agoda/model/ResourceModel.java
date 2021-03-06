package com.agoda.model;

public class ResourceModel {

  private String identifier;
  private String url;

  private String resourceName;
  private String username;
  private String password;

  public ResourceModel(String identifier, String url) {
    this.identifier = identifier;
    this.url = url;
  }

  public ResourceModel(
      String identifier, String url, String resourceName, String username, String password) {
    this.identifier = identifier;
    this.url = url;
    this.resourceName = resourceName;
    this.username = username;
    this.password = password;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }
}
