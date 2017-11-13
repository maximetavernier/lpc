package io.tatav.learningpc.views;

import io.tatav.learningpc.utils.LearningModelStatus;

/**
 * This interface is an extension for the list adapter class. It includes the
 * method to override in order to get the text corresponding to the model status
 * from the resource file.
 *
 * @author Tatav
 * @version 1.0
 */
public interface StatusDependent {
  String getTextByStatus(LearningModelStatus status);
}
