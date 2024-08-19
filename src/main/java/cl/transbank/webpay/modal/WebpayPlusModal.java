package cl.transbank.webpay.modal;

import cl.transbank.model.Options;

/**
 * This class provides methods to configure and perform transactions with the PatpassByWebpay service.
 */
public class WebpayPlusModal {

  /**
   * This class provides methods to configure and perform transactions with the WebpayPlusModal service.
   */
  public static class Transaction extends WebpayModalTransaction {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Transaction(Options options) {
      super(options);
    }
  }

}
