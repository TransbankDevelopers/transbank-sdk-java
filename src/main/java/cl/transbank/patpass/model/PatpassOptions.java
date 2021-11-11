package cl.transbank.patpass.model;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import lombok.*;

@ToString  @AllArgsConstructor
public class PatpassOptions extends Options   {
    @Getter  final String headerCommerceCodeName = "commercecode";
    @Getter  final String headerApiKeyName = "Authorization";

    public PatpassOptions(String commerceCode, String apiKey,IntegrationType integrationType ){
        super(commerceCode,apiKey,integrationType);
    }
}

