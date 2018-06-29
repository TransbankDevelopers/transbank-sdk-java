package cl.transbank.onepay;

import lombok.Getter;
import lombok.ToString;

public abstract class Onepay {
    public static volatile IntegrationType integrationType = IntegrationType.TEST;
    public static volatile String apiKey;
    public static volatile String appKey;
    public static volatile String callbackUrl;
    public static volatile String sharedSecret;

    @ToString public enum IntegrationType {
        LIVE(""),
        TEST("https://web2desa.test.transbank.cl");

        @Getter private String apiBase;

        IntegrationType(String apiBase) {
            this.apiBase = apiBase;
        }
    }
}
