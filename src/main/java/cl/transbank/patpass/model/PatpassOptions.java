package cl.transbank.patpass.model;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import lombok.*;

/**
 * This class represents the options for a Patpass transaction.
 */
@ToString
public class PatpassOptions extends Options {

  /**
   * Constructs a new PatpassOptions with the specified commerce code, API key, and integration type.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   * @param integrationType The integration type.
   */
  public PatpassOptions(
    String commerceCode,
    String apiKey,
    IntegrationType integrationType
  ) {
    super(commerceCode, apiKey, integrationType);
  }
}
