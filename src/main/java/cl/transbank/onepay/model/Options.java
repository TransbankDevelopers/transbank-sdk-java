package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Options {
    private String appKey;
    private String sharedSecret;

    public Options setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public Options setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
        return this;
    }

    public static Options getDefaults() {
        return new Options(Onepay.getAppKey(), Onepay.getSharedSecret());
    }
}
