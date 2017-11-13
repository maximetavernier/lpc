package io.tatav.learningpc.entities;

import java.util.ArrayList;

import io.tatav.learningpc.abs.entities.LearningModel;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;

/**
 * Created by Tatav on 15/02/2017.
 */

public final class LearningLesson extends LearningModel {
  private Guid quizzId;
  private LearningModelStatus status;
  private String contentFile;

  public Guid getQuizzId() {
    return quizzId;
  }

  public void setQuizzId(Guid quizzId) {
    this.quizzId = quizzId;
  }

  public LearningModelStatus getStatus() {
    return status;
  }

  public void setStatus(LearningModelStatus status) {
    this.status = status;
  }

  public String getContentFile() {
    return contentFile;
  }

  public void setContentFile(String contentFile) {
    this.contentFile = contentFile;
  }

}
