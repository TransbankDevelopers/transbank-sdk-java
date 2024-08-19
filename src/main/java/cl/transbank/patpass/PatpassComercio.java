package cl.transbank.patpass;

import cl.transbank.model.Options;

/**
 * This class represents the details of a MallTransactionCommit.
 */
public class PatpassComercio {

  /**
   * This class represents the details of a MallTransactionCommit.
   */
  public static class Inscription extends PatpassComercioInscription {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Inscription(Options options) {
      super(options);
    }
  }

}
