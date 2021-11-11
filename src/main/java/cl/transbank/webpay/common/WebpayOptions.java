package cl.transbank.webpay.common;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import lombok.*;

@ToString  @AllArgsConstructor
public class WebpayOptions extends Options {
    @Getter  final String headerCommerceCodeName = "Tbk-Api-Key-Id";
    @Getter  final String headerApiKeyName = "Tbk-Api-Key-Secret";

    public WebpayOptions(String commerceCode, String apiKey,IntegrationType integrationType ){
        super(commerceCode,apiKey,integrationType);

    }
}
