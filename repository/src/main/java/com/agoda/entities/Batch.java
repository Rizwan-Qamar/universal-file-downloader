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

  private BatchStatus status;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<BatchItem> batchItems;

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

  /** Batch items in this batch request. */
  public List<BatchItem> getBatchItems() {
    if (batchItems == null) batchItems = new ArrayList<BatchItem>();
    return batchItems;
  }

  public void setBatchItems(List<BatchItem> batchItems) {
    this.batchItems = batchItems;
  }

  public BatchStatus getStatus() {
    return status;
  }

  public void setStatus(BatchStatus status) {
    this.status = status;
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
        + ", status="
        + status
        + ", batchItems="
        + batchItems
        + '}';
  }
}
