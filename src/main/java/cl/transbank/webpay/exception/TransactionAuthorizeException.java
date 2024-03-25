package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction authorization fails.
 */
public class TransactionAuthorizeException extends WebpayException {

  /**
   * Constructs a new TransactionAuthorizeException with no detail message.
   */
  public TransactionAuthorizeException() {}

  /**
   * Constructs a new TransactionAuthorizeException with the specified cause.
   * @param e The cause of the exception.
   */
  public TransactionAuthorizeException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionAuthorizeException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionAuthorizeException(String message) {
    super(message);
  }
}
