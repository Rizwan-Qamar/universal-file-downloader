package com.agoda.core.interfaces;

import com.agoda.entities.BatchItem;

public interface BatchItemManagement {

  BatchItem query(String id);

  boolean markApproved(String id);

  boolean markRejected(String id);
}
