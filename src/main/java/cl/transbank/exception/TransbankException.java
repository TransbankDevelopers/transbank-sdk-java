package cl.transbank.exception;

import lombok.Getter;

/**
 * This class represents a general exception that is thrown when a Transbank operation fails.
 */
public class TransbankException extends Exception {

  @Getter
  private int code;

  /**
   * Constructs a new TransbankException with no detail message.
   */
  public TransbankException() {
    super();
  }

  /**
   * Constructs a new TransbankException with the specified detail message.
   */
  public TransbankException(String message) {
    super(message);
    this.code = -1;
  }

  /**
   * Constructs a new TransbankException with the specified detail message and error code.
   */
  public TransbankException(int code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Constructs a new TransbankException with the specified detail message, error code, and cause.
   */
  public TransbankException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * Constructs a new TransbankException with the specified cause.
   */
  public TransbankException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new TransbankException with the specified Ccode and cause.
   */
  public TransbankException(int code, Throwable cause) {
    super(cause);
    this.code = code;
  }

  /**
   * Constructs a new TransbankException with the specified error code and cause.
   */
  public TransbankException(
    int code,
    String message,
    Throwable cause,
    boolean enableSuppression,
    boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }
}
