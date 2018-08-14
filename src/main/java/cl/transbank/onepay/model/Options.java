package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Options {
    @Setter @Accessors(chain = true) private String apiKey;
    @Setter @Accessors(chain = true) private String sharedSecret;
    @Setter @Accessors(chain = true) private String channel;
    @Setter @Accessors(chain = true) private String callbackUrl;

    public static Options getDefaults() {
        return new Options(Onepay.getApiKey(), Onepay.getSharedSecret(), Onepay.DEFAULT_CHANNEL, Onepay.getCallbackUrl());
    }

    public static Options build(Options options) {
        if (null == options) return Options.getDefaults();

        if (null == options.getApiKey()) options.setApiKey(Onepay.getApiKey());
        if (null == options.getSharedSecret()) options.setSharedSecret(Onepay.getSharedSecret());
        if (null == options.getChannel()) options.setChannel(Onepay.DEFAULT_CHANNEL);
        if (null == options.getCallbackUrl()) options.setCallbackUrl(Onepay.getCallbackUrl());

        return options;
    }
}
