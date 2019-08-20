package cl.transbank.webpay;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.Options;
import lombok.*;

@ToString  @AllArgsConstructor
public class WebpayOptions extends Options implements Cloneable {
    @Getter  final String headerCommerceCodeName = "Tbk-Api-Key-Id";
    @Getter  final String headerApiKeyName = "Tbk-Api-Key-Secret";

    public Options buildOptions(Options options) {
        WebpayOptions alt = clone();

        // If the method receives an options object then rewrite each property, this is mandatory
        if (null != options) {
            if (null != options.getCommerceCode() && !options.getCommerceCode().trim().isEmpty())
                alt.setCommerceCode(options.getCommerceCode());

            if (null != options.getApiKey() && !options.getApiKey().trim().isEmpty())
                alt.setApiKey(options.getApiKey());

            if (null != options.getIntegrationType())
                alt.setIntegrationType(options.getIntegrationType());
        }

        return alt;
    }

    public WebpayOptions(String commerceCode, String apiKey,IntegrationType integrationType ){
        super(commerceCode,apiKey,integrationType);

    }

    @Override
    public WebpayOptions clone() {
        try {
            return (WebpayOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return (null == this.getCommerceCode() || this.getCommerceCode().trim().isEmpty()) &&
                (null == this.getApiKey() || this.getApiKey().trim().isEmpty()) &&
                (null == this.getIntegrationType() || this.getIntegrationType().toString().isEmpty());
    }

    public static boolean isEmpty(Options options) {
        return (null == options || (null == options.getCommerceCode() || options.getCommerceCode().trim().isEmpty()) &&
                (null == options.getApiKey() || options.getApiKey().trim().isEmpty()) &&
                (null == options.getIntegrationType() || options.getIntegrationType().toString().isEmpty()));
    }
}
