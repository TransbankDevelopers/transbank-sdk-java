package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when an inscription finish operation fails.
 */
public class InscriptionFinishException extends WebpayException {

  /**
   * Constructs a new InscriptionFinishException with no detail message.
   */
  public InscriptionFinishException() {}

  /**
   * Constructs a new InscriptionFinishException with the specified cause.
   * @param e Exception that caused the error.
   */
  public InscriptionFinishException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new InscriptionFinishException with the specified detail message.
   * @param message The detail message.
   */
  public InscriptionFinishException(String message) {
    super(message);
  }
}
