package io.tatav.learningpc.utils;

/**
 * Exception Class that is thrown when there is some error
 * coming from Guid class
 * @author Tatav
 * @version 1.0
 */
public final class GuidException extends Exception {
  public GuidException() {
    super();
  }

  public GuidException(String message) {
    super(message);
  }

  public GuidException(String message, Throwable cause) {
    super(message, cause);
  }

  public GuidException(Throwable cause) {
    super(cause);
  }
}
