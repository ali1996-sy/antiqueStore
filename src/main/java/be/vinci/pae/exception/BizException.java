package be.vinci.pae.exception;

import java.util.logging.Level;

import be.vinci.pae.utils.Loggers;

/**
 * The Class BizException.
 */
public class BizException extends RuntimeException {



  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;


  /**
   * Instantiates a new biz exception.
   */
  public BizException() {
    super();
  }


  /**
   * Instantiates a new biz exception.
   *
   * @param message the message
   */
  public BizException(String message) {
    super(message);
    Loggers.logException(Level.WARNING, this);

  }
}
