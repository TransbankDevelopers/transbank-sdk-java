package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction status operation fails.
 */
public class TransactionStatusException extends WebpayException {

  /**
   * Constructs a new TransactionStatusException with no detail message.
   */
  public TransactionStatusException() {
    super();
  }

  /**
   * Constructs a new TransactionStatusException with the specified cause.
   * @param e The cause of the exception.
   */
  public TransactionStatusException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionStatusException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionStatusException(String message) {
    super(message);
  }
}
