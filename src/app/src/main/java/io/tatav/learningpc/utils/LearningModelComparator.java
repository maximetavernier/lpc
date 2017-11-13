package io.tatav.learningpc.utils;

import java.util.Comparator;

import io.tatav.learningpc.abs.entities.LearningModel;

/**
 * Created by Tatav on 10/03/2017.
 */

public class LearningModelComparator implements Comparator<LearningModel> {
  @Override
  public int compare(LearningModel o1, LearningModel o2) {
    return o1.compareTo(o2);
  }
}
