package cl.transbank.webpay.oneclick;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;

/**
 * This class provides methods to configure and perform transactions with the Oneclick service.
 */
public class Oneclick {

  private static Options options;

  /**
   * This class provides methods to perform Oneclick Mall Inscriptions.
   */
  public static class MallInscription extends OneclickMallInscription {

    public MallInscription() {
      super(
        Oneclick.options != null
          ? Oneclick.options
          : new WebpayOptions(
            IntegrationCommerceCodes.ONECLICK_MALL,
            IntegrationApiKeys.WEBPAY,
            IntegrationType.TEST
          )
      );
    }

    public MallInscription(Options options) {
      super(options);
    }
  }

  /**
   * Default constructor. Uses default options if none are provided.
   */
  public static class MallTransaction extends OneclickMallTransaction {

    public MallTransaction() {
      super(
        Oneclick.options != null
          ? Oneclick.options
          : new WebpayOptions(
            IntegrationCommerceCodes.ONECLICK_MALL,
            IntegrationApiKeys.WEBPAY,
            IntegrationType.TEST
          )
      );
    }

    public MallTransaction(Options options) {
      super(options);
    }
  }

  /*
    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

  public static void configureForIntegration(
    String commerceCode,
    String apiKey
  ) {
    options = new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST);
  }

  public static void configureForProduction(
    String commerceCode,
    String apiKey
  ) {
    options = new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE);
  }

  public static void configureForTesting() {
    configureForIntegration(
      IntegrationCommerceCodes.ONECLICK_MALL,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForTestingDeferred() {
    configureForIntegration(
      IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForMock() {
    options =
      new WebpayOptions(
        IntegrationCommerceCodes.ONECLICK_MALL,
        IntegrationApiKeys.WEBPAY,
        IntegrationType.SERVER_MOCK
      );
  }
}
