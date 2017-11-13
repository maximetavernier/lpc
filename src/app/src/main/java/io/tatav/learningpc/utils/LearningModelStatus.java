package io.tatav.learningpc.utils;

/**
 * Status of a learning entity (Lesson, tuto or quizz). There are different
 * value of the status for a learning entity :
 *   ¤ 0 : The entity has to be done (TODO)
 *   ¤ 1 : The entity is being done (DOING)
 *   ¤ 2 : The entity is done (DONE)
 * There is also a value that allow to manage error. When the {@link LearningModelStatus}
 * value is -1, it means that the status is UNKNOWN.
 * @author Tatav
 * @version 1.0
 */
public enum LearningModelStatus {
  UNKNOWN(-1),
  TODO(0),
  DOING(1),
  DONE(2);

  private final int value;

  LearningModelStatus(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }

  public static LearningModelStatus getStatus(int value)
          throws LearningModelStatusException {
    LearningModelStatus status = value == 0 ? TODO : value == 1 ? DOING : value == 2 ? DONE : UNKNOWN;

    if (status == UNKNOWN)
      throw new LearningModelStatusException();
    return status;
  }
}
