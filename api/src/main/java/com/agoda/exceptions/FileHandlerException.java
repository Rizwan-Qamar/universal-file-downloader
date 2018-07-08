package com.agoda.exceptions;

import com.agoda.model.ResourceModel;

public class FileHandlerException extends Exception {

  ResourceModel resourceModel;

  public FileHandlerException(String s, Exception e, ResourceModel resourceModel) {
    super(s, e);
    this.resourceModel = resourceModel;
  }

  public ResourceModel getResourceModel() {
    return resourceModel;
  }

  public void setResourceModel(ResourceModel resourceModel) {
    this.resourceModel = resourceModel;
  }
}
