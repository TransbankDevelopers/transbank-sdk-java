package cl.transbank.common;

/**
 * This class provides helper methods to get the integration type for Webpay and Patpass.
 */
public class IntegrationTypeHelper {

  /**
   * Returns the Webpay integration type based on the provided integration type.
   */
  public static String getWebpayIntegrationType(
    IntegrationType integrationType
  ) {
    switch (integrationType) {
      case LIVE:
        return "https://webpay3g.transbank.cl";
      case MOCK:
        return "";
      case TEST:
        return "https://webpay3gint.transbank.cl";
      case SERVER_MOCK:
        return "http://localhost:8888";
      default:
        return "https://webpay3gint.transbank.cl";
    }
  }

  /**
   * Returns the Patpass integration type based on the provided integration type.
   */
  public static String getPatpassIntegrationType(
    IntegrationType integrationType
  ) {
    switch (integrationType) {
      case LIVE:
        return "https://www.pagoautomaticocontarjetas.cl";
      case MOCK:
        return "";
      case TEST:
        return "https://pagoautomaticocontarjetasint.transbank.cl";
      case SERVER_MOCK:
        return "http://localhost:8888";
      default:
        return "https://pagoautomaticocontarjetasint.transbank.cl";
    }
  }
}
