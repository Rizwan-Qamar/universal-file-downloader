package com.agoda.entities;

import com.agoda.util.IdGenerator;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class BatchItem implements Serializable {

  @Id private String id;

  private String resourceLocation;

  private BatchItemStatus status;

  private String locationOnDisk;

  public BatchItem() {};

  @PrePersist
  public void ensureId() {
    setId(IdGenerator.createUUID());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getResourceLocation() {
    return resourceLocation;
  }

  public void setResourceLocation(String resourceLocation) {
    this.resourceLocation = resourceLocation;
  }

  public BatchItemStatus getStatus() {
    return status;
  }

  public void setStatus(BatchItemStatus status) {
    this.status = status;
  }

  public String getLocationOnDisk() {
    return locationOnDisk;
  }

  public void setLocationOnDisk(String locationOnDisk) {
    this.locationOnDisk = locationOnDisk;
  }

  @Override
  public String toString() {
    return "BatchItem{"
        + "id='"
        + id
        + '\''
        + ", resourceLocation='"
        + resourceLocation
        + '\''
        + ", status="
        + status
        + ", locationOnDisk='"
        + locationOnDisk
        + '\''
        + '}';
  }
}
