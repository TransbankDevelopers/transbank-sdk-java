package cl.transbank.patpass;

import lombok.Getter;

public enum IntegrationType {
    LIVE("https://www.pagoautomaticocontarjetas.cl"),
    TEST(""),
    MOCK("");

    @Getter
    private String apiBase;

    IntegrationType(String apiBase) {
        this.apiBase = apiBase;
    }
}
