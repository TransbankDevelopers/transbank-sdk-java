package cl.transbank.webpay.exception;

import cl.transbank.exception.TransbankException;

/**
 * This class represents an exception that is thrown when a Webpay operation fails.
 */
public class WebpayException extends TransbankException {

  /**
   * Constructs a new WebpayException with no detail message.
   */
  public WebpayException() {
    super();
  }

  /**
   * Constructs a new WebpayException with the specified cause.
   */
  public WebpayException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new WebpayException with the specified detail message.
   */
  public WebpayException(String message) {
    super(message);
  }

  /**
   * Constructs a new WebpayException with the specified detail message and error code.
   */
  public WebpayException(int code, String message) {
    super(code, message);
  }

  /**
   * Returns a string representation of this exception.
   */
  @Override
  public String toString() {
    return super.toString().concat(String.format(" | code : %s", getCode()));
  }
}
