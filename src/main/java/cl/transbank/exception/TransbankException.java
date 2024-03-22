package cl.transbank.exception;

import lombok.Getter;

/**
 * This class represents a general exception that is thrown when a Transbank operation fails.
 */
public class TransbankException extends Exception {

  @Getter
  private final int code;

  /**
   * Constructs a new TransbankException with no detail message.
   */
  public TransbankException() {
    super();
    this.code = -1;
  }

  /**
   * Constructs a new TransbankException with the specified detail message.
   * @param message The detail message.
   */
  public TransbankException(String message) {
    super(message);
    this.code = -1;
  }

  /**
   * Constructs a new TransbankException with the specified detail message and error code.
   * @param code The error code.
   * @param message The detail message.
   */
  public TransbankException(int code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Constructs a new TransbankException with the specified detail message, error code, and cause.
   * @param code The error code.
   * @param message The detail message.
   * @param cause The cause.
   */
  public TransbankException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * Constructs a new TransbankException with the specified cause.
   * @param cause The cause.
   */
  public TransbankException(Throwable cause) {
    super(cause);
    this.code = -1;
  }

  /**
   * Constructs a new TransbankException with the specified Ccode and cause.
   * @param code The error code.
   * @param cause The cause.
   */
  public TransbankException(int code, Throwable cause) {
    super(cause);
    this.code = code;
  }

  /**
   * Constructs a new TransbankException with the specified error code and cause.
   * @param code The error code.
   * @param message The detail message.
   * @param cause The cause.
   * @param enableSuppression Whether or not suppression is enabled or disabled.
   * @param writableStackTrace Whether or not the stack trace should be writable.
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
