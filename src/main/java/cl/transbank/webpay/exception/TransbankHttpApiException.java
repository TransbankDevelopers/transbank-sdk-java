package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a Transbank HTTP API operation fails.
 */
public class TransbankHttpApiException extends WebpayException {

  /**
   * Constructs a new TransbankHttpApiException with no detail message.
   */
  public TransbankHttpApiException() {}

  /**
   * Constructs a new TransbankHttpApiException with the specified cause.
   * @param e Exception that caused the error.
   */
  public TransbankHttpApiException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransbankHttpApiException with the specified detail message.
   * @param message The detail message.
   */
  public TransbankHttpApiException(String message) {
    super(message);
  }

  /**
   * Constructs a new TransbankHttpApiException with the specified detail message and error code.
   * @param code The error code.
   * @param message The detail message.
   */
  public TransbankHttpApiException(int code, String message) {
    super(code, message);
  }
}
