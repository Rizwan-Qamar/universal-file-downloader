package com.agoda.entities;

import com.agoda.util.IdGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Batch implements Serializable {
  @Id private String id;

  private String createdAt;

  private String updatedAt;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> urls;

  public Batch() {}

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

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  /** Resource URLs in this batch request. */
  public List<String> getUrls() {
    if (urls == null) urls = new ArrayList<String>();
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  @Override
  public String toString() {
    return "Batch{"
        + "id='"
        + id
        + '\''
        + ", createdAt='"
        + createdAt
        + '\''
        + ", updatedAt='"
        + updatedAt
        + '\''
        + '}';
  }
}
