package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction creation fails.
 */
public class TransactionCreateException extends WebpayException {

  /**
   * Constructs a new TransactionCreateException with no detail message.
   */
  public TransactionCreateException() {
    super();
  }

  /**
   * Constructs a new TransactionCreateException with the specified cause.
   */
  public TransactionCreateException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionCreateException with the specified detail message.
   */
  public TransactionCreateException(String message) {
    super(message);
  }
}
