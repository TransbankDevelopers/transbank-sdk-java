package cl.transbank.model;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationType;
import lombok.*;

/**
 * This abstract class represents the options that can be set for a transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class Options implements Cloneable {

  @Getter
  private String commerceCode;

  @Getter
  private String apiKey;

  @Getter
  private IntegrationType integrationType;

  /**
   * Builds the options for a transaction.
   * @param options The options to set.
   * @return The built options.
   */
  public Options buildOptions(Options options) {
    Options alt = clone();

    // If the method receives an options object then rewrite each property, this is mandatory
    if (null != options) {
      if (
        null != options.getCommerceCode() &&
        !options.getCommerceCode().trim().isEmpty()
      ) alt.setCommerceCode(options.getCommerceCode());

      if (
        null != options.getApiKey() && !options.getApiKey().trim().isEmpty()
      ) alt.setApiKey(options.getApiKey());

      if (null != options.getIntegrationType()) alt.setIntegrationType(
        options.getIntegrationType()
      );
    }

    return alt;
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
