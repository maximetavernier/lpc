package io.tatav.learningpc.entities;

import java.util.ArrayList;

import io.tatav.learningpc.abs.entities.LearningModel;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;

/**
 * Created by Tatav on 15/02/2017.
 */

public final class LearningQuizz extends LearningModel {
  private Guid lessonId;
  private LearningModelStatus status;
  private String introduction;
  private String conclusion;
  private ArrayList<QuizzPart> parts;

  public Guid getLessonId() {
    return lessonId;
  }

  public void setLessonId(Guid lessonId) {
    this.lessonId = lessonId;
  }

  public LearningModelStatus getStatus() {
    return status;
  }

  public void setStatus(LearningModelStatus status) {
    this.status = status;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public String getConclusion() {
    return conclusion;
  }

  public void setConclusion(String conclusion) {
    this.conclusion = conclusion;
  }

  public ArrayList<QuizzPart> getParts() {
    return parts;
  }

  public void setParts(ArrayList<QuizzPart> parts) {
    this.parts = parts;
  }
}
