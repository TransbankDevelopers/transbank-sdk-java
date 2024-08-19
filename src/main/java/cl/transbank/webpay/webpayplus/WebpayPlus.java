package cl.transbank.webpay.webpayplus;

import cl.transbank.model.Options;

/**
 * This class provides methods to configure and perform transactions with the WebpayPlus service.
 */
public class WebpayPlus {

  /**
   * This class provides methods to perform WebpayPlus Transactions.
   */
  public static class Transaction extends WebpayTransaction {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Transaction(Options options) {
      super(options);
    }
  }

  /**
   * This class provides methods to perform WebpayPlus Mall Transactions.
   */
  public static class MallTransaction extends WebpayMallTransaction {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public MallTransaction(Options options) {
      super(options);
    }
  }

}
