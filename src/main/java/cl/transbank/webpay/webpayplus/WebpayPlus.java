package cl.transbank.webpay.webpayplus;

import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class WebpayPlus {
    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static void setCommerceCode(String commerceCode) {
        WebpayPlus.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return WebpayPlus.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        WebpayPlus.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return WebpayPlus.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        WebpayPlus.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return WebpayPlus.getOptions().getIntegrationType();
    }

    public static void configureForWebpayPlusNormal() {
        WebpayPlus.setOptions(new Options("597055555532",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static void configureForWebpayPlusMall() {
        // TODO we have not the commerce code yet
        WebpayPlus.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static void configureForWebpayPlusDeferred() {
        // TODO we have not the commerce code yet
        WebpayPlus.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }
}