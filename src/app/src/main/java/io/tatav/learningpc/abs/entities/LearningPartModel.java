package io.tatav.learningpc.abs.entities;

import java.io.Serializable;

import io.tatav.learningpc.utils.Guid;

/**
 * Created by Tatav on 23/02/2017.
 */

public abstract class LearningPartModel implements Serializable {
  private Guid id;

  public Guid getId() {
    return id;
  }

  public void setId(Guid id) {
    this.id = id;
  }
}
