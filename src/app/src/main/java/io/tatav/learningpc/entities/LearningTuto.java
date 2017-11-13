package io.tatav.learningpc.entities;

import io.tatav.learningpc.abs.entities.LearningModel;

/**
 * Created by Tatav on 11/03/2017.
 */

public class LearningTuto extends LearningModel {
  private String contentFile;

  public String getContentFile() {
    return contentFile;
  }

  public void setContentFile(String contentFile) {
    this.contentFile = contentFile;
  }
}
