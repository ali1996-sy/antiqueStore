package be.vinci.pae.exception;

import java.util.logging.Level;

import be.vinci.pae.utils.Loggers;


/**
 * The Class FatalException.
 */
public class FatalException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;


  /**
   * Instantiates a new fatal exception.
   *
   * @param message the message
   */
  public FatalException(String message) {
    super(message);

    Loggers.logException(Level.SEVERE, this);



  }

  /**
   * Instantiates a new fatal exception.
   *
   * @param cause the cause
   */
  public FatalException(Throwable cause) {
    super(cause);

    Loggers.logException(Level.WARNING, this);

  }

  /**
   * Instantiates a new fatal exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public FatalException(String message, Throwable cause) {
    super(message, cause);

    Loggers.logException(Level.WARNING, this);

  }

}
