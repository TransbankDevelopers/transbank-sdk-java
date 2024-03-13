package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction capture operation fails.
 */
public class TransactionCaptureException extends WebpayException {

  /**
   * Constructs a new TransactionCaptureException with null as its detail message.
   */
  public TransactionCaptureException() {
    super();
  }

  /**
   * Constructs a new TransactionCaptureException with the specified cause.
   * @param e The cause of the exception.
   */
  public TransactionCaptureException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionCaptureException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionCaptureException(String message) {
    super(message);
  }
}
