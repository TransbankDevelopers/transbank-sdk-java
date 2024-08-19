package cl.transbank.webpay.oneclick;

import cl.transbank.model.Options;

/**
 * This class provides methods to configure and perform transactions with the Oneclick service.
 */
public class Oneclick {

  /**
   * This class provides methods to perform Oneclick Mall Inscriptions.
   */
  public static class MallInscription extends OneclickMallInscription {

    public MallInscription(Options options) {
      super(options);
    }
  }

  /**
   * Default constructor. Uses default options if none are provided.
   */
  public static class MallTransaction extends OneclickMallTransaction {

    public MallTransaction(Options options) {
      super(options);
    }
  }

}
