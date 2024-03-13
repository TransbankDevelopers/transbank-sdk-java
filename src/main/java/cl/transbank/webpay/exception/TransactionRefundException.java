package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction refund operation fails.
 */
public class TransactionRefundException extends WebpayException {

  /**
   * Constructs a new TransactionRefundException with no detail message.
   */
  public TransactionRefundException() {
    super();
  }

  /**
   * Constructs a new TransactionRefundException with the specified cause.
   * @param e The cause of the exception.
   */
  public TransactionRefundException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionRefundException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionRefundException(String message) {
    super(message);
  }
}
