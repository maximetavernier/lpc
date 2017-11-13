package io.tatav.learningpc.abs.entities;

import java.io.Serializable;

import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.Guid;

/**
 * Created by Tatav on 15/02/2017.
 */

public abstract class LearningModel implements Serializable, Comparable<LearningModel> {
  private Guid id;
  private String title;
  private String description;
  private Float difficulty;
  private String picture;

  public Guid getId() {
    return id;
  }

  public void setId(Guid id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Float difficulty) {
    this.difficulty = difficulty;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  @Override
  public int compareTo(LearningModel model) {
    return (int) (this.difficulty * 100 - model.difficulty * 100);
  }

  @Override
  public boolean equals(Object o) {
    return id.equals(((LearningModel)o).getId());
  }
}
