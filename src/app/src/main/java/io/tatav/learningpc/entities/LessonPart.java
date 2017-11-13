package io.tatav.learningpc.entities;

import io.tatav.learningpc.abs.entities.LearningPartModel;

/**
 * Created by Tatav on 23/02/2017.
 */

public final class LessonPart extends LearningPartModel {
  private String title;
  private String content;
  private String picture;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
