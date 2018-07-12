package cl.transbank.onepay;

import lombok.Getter;
import lombok.ToString;

public abstract class Onepay {
    public static final String API_KEY = "mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg";
    private static volatile IntegrationType integrationType = IntegrationType.TEST;
    private static volatile String appKey;
    private static volatile String callbackUrl;
    private static volatile String sharedSecret;

    public static IntegrationType getIntegrationType() {
        return integrationType;
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        if (null == integrationType) throw new NullPointerException("integrationType cannot be null");
        Onepay.integrationType = integrationType;
    }

    public static String getAppKey() {
        return appKey;
    }

    public static void setAppKey(String appKey) {
        if (null == appKey) throw new NullPointerException("appKey cannot be null");
        Onepay.appKey = appKey;
    }

    public static String getCallbackUrl() {
        return callbackUrl;
    }

    public static void setCallbackUrl(String callbackUrl) {
        if (null == callbackUrl) throw new NullPointerException("callbackUrl cannot be null");
        Onepay.callbackUrl = callbackUrl;
    }

    public static String getSharedSecret() {
        return sharedSecret;
    }

    public static void setSharedSecret(String sharedSecret) {
        if (null == sharedSecret) throw new NullPointerException("sharedSecret cannot be null");
        Onepay.sharedSecret = sharedSecret;
    }

    @ToString public enum IntegrationType {
        LIVE(""),
        TEST("https://web2desa.test.transbank.cl");

        @Getter private String apiBase;

        IntegrationType(String apiBase) {
            this.apiBase = apiBase;
        }
    }
}
