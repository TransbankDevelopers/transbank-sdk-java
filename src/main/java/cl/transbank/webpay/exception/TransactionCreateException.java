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
   * @param e Exception that caused the error.
   */
  public TransactionCreateException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionCreateException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionCreateException(String message) {
    super(message);
  }
}
