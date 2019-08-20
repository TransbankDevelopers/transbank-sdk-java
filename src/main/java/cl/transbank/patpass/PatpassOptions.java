package cl.transbank.patpass;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.Options;
import lombok.*;

@ToString  @AllArgsConstructor
public class PatpassOptions extends Options implements Cloneable  {
    @Getter  final String headerCommerceCodeName = "commercecode";
    @Getter  final String headerApiKeyName = "Authorization";

    public Options buildOptions(Options patpassOptions) {
        PatpassOptions alt = clone();

        // If the method receives an options object then rewrite each property, this is mandatory
        if (null != patpassOptions) {
            if (null != patpassOptions.getCommerceCode() && !patpassOptions.getCommerceCode().trim().isEmpty())
                alt.setCommerceCode(patpassOptions.getCommerceCode());

            if (null != patpassOptions.getApiKey() && !patpassOptions.getApiKey().trim().isEmpty())
                alt.setApiKey(patpassOptions.getApiKey());

            if (null != patpassOptions.getIntegrationType())
                alt.setIntegrationType(patpassOptions.getIntegrationType());
        }

        return alt;
    }

    public PatpassOptions(String commerceCode, String apiKey,IntegrationType integrationType ){
        super(commerceCode,apiKey,integrationType);
    }

    @Override
    public PatpassOptions clone() {
        try {
            return (PatpassOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return  (null == this.getCommerceCode() || this.getCommerceCode().trim().isEmpty()) &&
                (null == this.getApiKey() || this.getApiKey().trim().isEmpty()) &&
                (null == this.getIntegrationType() || this.getIntegrationType().toString().isEmpty());
    }

    public static boolean isEmpty(Options patpassOptions) {
        return (null == patpassOptions || (null == patpassOptions.getCommerceCode() || patpassOptions.getCommerceCode().trim().isEmpty()) &&
                (null == patpassOptions.getApiKey() || patpassOptions.getApiKey().trim().isEmpty()) &&
                (null == patpassOptions.getIntegrationType() || patpassOptions.getIntegrationType().toString().isEmpty()));
    }
}

