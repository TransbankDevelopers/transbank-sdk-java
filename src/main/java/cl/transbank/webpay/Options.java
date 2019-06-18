package cl.transbank.webpay;

import lombok.*;

@ToString @NoArgsConstructor @AllArgsConstructor
public class Options {
    @Setter @Getter private String commerceCode;
    @Setter @Getter private String apiKey;
    @Setter @Getter private IntegrationType integrationType;
}
