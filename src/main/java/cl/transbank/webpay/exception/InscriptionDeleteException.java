package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when an inscription deletion operation fails.
 */
public class InscriptionDeleteException extends WebpayException {

  /**
   * Constructs a new InscriptionDeleteException with no detail message.
   */
  public InscriptionDeleteException() {}

  /**
   * Constructs a new InscriptionDeleteException with the specified cause.
   * @param e The cause of the exception.
   */
  public InscriptionDeleteException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new InscriptionDeleteException with the specified detail message.
   * @param message The detail message.
   */
  public InscriptionDeleteException(String message) {
    super(message);
  }
}
