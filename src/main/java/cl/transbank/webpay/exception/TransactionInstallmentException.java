package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a transaction installment operation fails.
 */
public class TransactionInstallmentException extends WebpayException {

  /**
   * Constructs a new TransactionInstallmentException with no detail message.
   */
  public TransactionInstallmentException() {
    super();
  }

  /**
   * Constructs a new TransactionInstallmentException with the specified cause.
   * @param e The cause of the exception.
   */
  public TransactionInstallmentException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new TransactionInstallmentException with the specified detail message.
   * @param message The detail message.
   */
  public TransactionInstallmentException(String message) {
    super(message);
  }
}
