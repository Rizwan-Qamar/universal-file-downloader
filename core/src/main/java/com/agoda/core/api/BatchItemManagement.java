package com.agoda.core.api;

import com.agoda.entities.BatchItem;
import com.agoda.entities.BatchItemStatus;
import com.agoda.repositories.BatchItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@ConfigurationProperties
public class BatchItemManagement implements com.agoda.core.interfaces.BatchItemManagement {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private BatchItemRepository batchItemRepository;

  @Override
  public BatchItem query(String id) {
    return batchItemRepository.findOne(id);
  }

  @Override
  public boolean markApproved(String id) {
    BatchItem batchItem = batchItemRepository.findOne(id);
    if (batchItem != null) {
      batchItem.setStatus(BatchItemStatus.APPROVED);
      batchItemRepository.save(batchItem);
      return true;
    }
    return false;
  }

  @Override
  public boolean markRejected(String id) {
    BatchItem batchItem = batchItemRepository.findOne(id);
    if (batchItem != null) {
      batchItem.setStatus(BatchItemStatus.REJECTED);
      batchItemRepository.save(batchItem);
      return true;
    }
    return false;
  }
}
