package cl.transbank.webpay.oneclick;

import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Oneclick {
    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static void setCommerceCode(String commerceCode) {
        Oneclick.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return Oneclick.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        Oneclick.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return Oneclick.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        Oneclick.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return Oneclick.getOptions().getIntegrationType();
    }

    public static void configureMallForTesting() {
        // TODO we have not the commerce code yet
        Oneclick.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static void configureDeferredForTesting() {
        // TODO we have not the commerce code yet
        Oneclick.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }
}
