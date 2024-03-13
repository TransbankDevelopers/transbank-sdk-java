package cl.transbank.webpay.common;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import lombok.*;

/**
 * This class represents the options for a Webpay transaction.
 */
@ToString
@AllArgsConstructor
public class WebpayOptions extends Options {

  @Getter
  final String headerCommerceCodeName = "Tbk-Api-Key-Id";

  @Getter
  final String headerApiKeyName = "Tbk-Api-Key-Secret";

  /**
   * Constructs a new WebpayOptions with the specified commerce code, API key, and integration type.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   * @param integrationType The integration type.
   */
  public WebpayOptions(
    String commerceCode,
    String apiKey,
    IntegrationType integrationType
  ) {
    super(commerceCode, apiKey, integrationType);
  }
}
