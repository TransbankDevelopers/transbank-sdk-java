package cl.transbank.webpay;

import lombok.*;
import lombok.experimental.Accessors;

@ToString @NoArgsConstructor @AllArgsConstructor
public class Options implements Cloneable {
    @Setter @Getter @Accessors(chain = true) private String commerceCode;
    @Setter @Getter @Accessors(chain = true) private String apiKey;
    @Setter @Getter @Accessors(chain = true) private IntegrationType integrationType;

    public Options buildOptions(Options options) {
        Options alt = clone();

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

    @Override
    public Options clone() {
        try {
            return (Options) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return (null == commerceCode || commerceCode.trim().isEmpty()) &&
                (null == apiKey || apiKey.trim().isEmpty()) &&
                (null == integrationType || integrationType.toString().isEmpty());
    }

    public static boolean isEmpty(Options options) {
        return (null == options || (null == options.getCommerceCode() || options.getCommerceCode().trim().isEmpty()) &&
                (null == options.getApiKey() || options.getApiKey().trim().isEmpty()) &&
                (null == options.getIntegrationType() || options.getIntegrationType().toString().isEmpty()));
    }
}
