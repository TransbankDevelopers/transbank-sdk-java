package cl.transbank.model;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationType;
import lombok.*;

/**
 * This abstract class represents the options that can be set for a transaction.
 */
public abstract class Options implements Cloneable {

  @Getter
  private String commerceCode;

  @Getter
  private String apiKey;

  @Getter
  private IntegrationType integrationType;

  @Setter
  @Getter
  private int timeout;

  /**
   * Constructs a new Options with the specified commerce code, API key, and integration type.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   * @param integrationType The integration type.
   */
  protected Options(
    String commerceCode,
    String apiKey,
    IntegrationType integrationType
  ) {
    this.commerceCode = commerceCode;
    this.apiKey = apiKey;
    this.integrationType = integrationType;
    this.timeout = ApiConstants.REQUEST_TIMEOUT;
  }

  /**
   * Constructs a new Options with the specified commerce code, API key, integration type, and timeout.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   * @param integrationType The integration type.
   * @param timeout The time in ms to wait for a response.
   */
  protected Options(
    String commerceCode,
    String apiKey,
    IntegrationType integrationType,
    int timeout
  ) {
    this.commerceCode = commerceCode;
    this.apiKey = apiKey;
    this.integrationType = integrationType;
    this.timeout = timeout;
  }

  /**
   * Creates and returns a copy of this object.
   * @return A clone of this instance.
   */
  @Override
  public Options clone() {
    try {
      return (Options) super.clone();
    } catch (CloneNotSupportedException e) {
      return (Options) new Object();
    }
  }

  /**
   * Checks if the options are empty.
   * @return true if the options are empty, false otherwise.
   */
  public boolean isEmpty() {
    return (
      (
        null == this.getCommerceCode() ||
        this.getCommerceCode().trim().isEmpty()
      ) &&
      (null == this.getApiKey() || this.getApiKey().trim().isEmpty()) &&
      (
        null == this.getIntegrationType() ||
        this.getIntegrationType().toString().isEmpty()
      )
    );
  }

  public static boolean isEmpty(Options options) {
    return (
      null == options ||
      (
        null == options.getCommerceCode() ||
        options.getCommerceCode().trim().isEmpty()
      ) &&
      (null == options.getApiKey() || options.getApiKey().trim().isEmpty()) &&
      (
        null == options.getIntegrationType() ||
        options.getIntegrationType().toString().isEmpty()
      )
    );
  }
}
