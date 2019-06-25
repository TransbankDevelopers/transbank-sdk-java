package cl.transbank.webpay;

import lombok.Getter;

public enum IntegrationType {
    LIVE("https://webpay3g.transbank.cl"),
    TEST("https://webpay3gint.transbank.cl"),
    MOCK("");

    @Getter private String apiBase;

    IntegrationType(String apiBase) {
        this.apiBase = apiBase;
    }
}