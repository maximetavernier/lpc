package io.tatav.learningpc.abs.activities;

import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;

/**
 * Created by Tatav on 11/08/2017.
 */

public interface StatusUpdater {
  void updateStatus(String tag, Guid entityId, LearningModelStatus status);
}
