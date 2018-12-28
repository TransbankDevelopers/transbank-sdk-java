package cl.transbank.onepay;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

public abstract class Onepay {
    public static final Channel DEFAULT_CHANNEL = Channel.WEB;
    public static final String DEFAULT_CALLBACK = "http://no.callback.has/been.set";
    @NonNull @Setter @Getter private static IntegrationType integrationType = IntegrationType.TEST;
    @Setter @Getter private static String apiKey;
    @Setter @Getter private static String sharedSecret;
    @Setter @Getter private static String callbackUrl;
    @Setter @Getter private static String appScheme;
    @Setter @Getter private static Integer qrWidthHeight;
    @Setter @Getter private static String commerceLogoUrl;

    static { setIntegrationApiKeyAndSharedSecret(); }

    public static String getCurrentIntegrationTypeUrl() {
        return String.format(
                "%s/ewallet-plugin-api-services/services/transactionservice",
                getIntegrationType().getApiBase());

    }

    /**
     * Sets the credentials published by Transbank to play on the TEST
     * environment.
     */
    public static void setIntegrationApiKeyAndSharedSecret() {
        setApiKey("dKVhq1WGt_XapIYirTXNyUKoWTDFfxaEV63-O5jcsdw");
        setSharedSecret("?XW#WOLG##FBAGEAYSNQ5APD#JF@$AYZ");
    }

    @ToString public enum IntegrationType {
        LIVE("https://www.onepay.cl", "7968CDF8-F4CC-4BC5-8E27-D0513B88EB95"),
        TEST("https://onepay.ionix.cl", "fe9d371d-10ae-4138-8cfb-e2215b82c0d3"),
        MOCK("https://transbank-onepay-ewallet-mock.herokuapp.com", "04533c31-fe7e-43ed-bbc4-1c8ab1538afp");

        @Getter private String apiBase;
        @Getter private String appKey;

        IntegrationType(String apiBase, String appKey) {
            this.apiBase = apiBase;
            this.appKey = appKey;
        }
    }

    public enum Channel {
        WEB,
        MOBILE,
        APP
    }
}
