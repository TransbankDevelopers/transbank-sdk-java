package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when an inscription start operation fails.
 */
public class InscriptionStartException extends WebpayException {

  /**
   * Constructs a new InscriptionStartException with no detail message.
   */
  public InscriptionStartException() {
    super();
  }

  /**
   * Constructs a new InscriptionStartException with the specified cause.
   * @param e The cause of the exception.
   */
  public InscriptionStartException(Exception e) {
    super(e);
  }

  /**
   * Constructs a new InscriptionStartException with the specified detail message.
   * @param message The detail message.
   */
  public InscriptionStartException(String message) {
    super(message);
  }
}
