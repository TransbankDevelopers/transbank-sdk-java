package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction commit operation fails.
 */
public class TransactionCommitException extends WebpayException {

  /**
   * Constructs a new TransactionCommitException with no detail message.
   */
  public TransactionCommitException() {
    super();
  }

  /**
   * Constructs a new TransactionCommitException with the specified cause.
   * @param e The cause of the exception.
   */
  public TransactionCommitException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionCommitException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionCommitException(String message) {
    super(message);
  }
}
