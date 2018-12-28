package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Options {
    @NonNull @Setter @Accessors(chain = true) private String apiKey;
    @NonNull @Setter @Accessors(chain = true) private String sharedSecret;
    @Setter @Accessors(chain = true) private Integer qrWidthHeight;
    @Setter @Accessors(chain = true) private String commerceLogoUrl;

    public static Options getDefaults() {
        return new Options(
                Onepay.getApiKey(),
                Onepay.getSharedSecret(),
                Onepay.getQrWidthHeight(),
                Onepay.getCommerceLogoUrl());
    }

    public static Options build(Options options) {
        if (null == options) return Options.getDefaults();

        if (null == options.getApiKey()) options.setApiKey(Onepay.getApiKey());
        if (null == options.getSharedSecret()) options.setSharedSecret(Onepay.getSharedSecret());
        if (null == options.getQrWidthHeight()) options.setQrWidthHeight(Onepay.getQrWidthHeight());
        if (null == options.getCommerceLogoUrl()) options.setCommerceLogoUrl(Onepay.getCommerceLogoUrl());

        return options;
    }

}
