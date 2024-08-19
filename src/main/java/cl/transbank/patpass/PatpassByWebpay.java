package cl.transbank.patpass;

import cl.transbank.model.Options;

/**
 * This class provides methods to configure and perform transactions with the PatpassByWebpay service.
 */
public class PatpassByWebpay {

  /**
   * This class provides methods to configure and perform transactions with the PatpassByWebpay service.
   */
  public static class Transaction extends PatpassByWebpayTransaction {

    public Transaction(Options options) {
      super(options);
    }
  }

}
