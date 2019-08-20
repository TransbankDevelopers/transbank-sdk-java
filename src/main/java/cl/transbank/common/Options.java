package cl.transbank.common;

import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Options {
    @Setter @Getter @Accessors(chain = true) private String commerceCode;
    @Setter @Getter @Accessors(chain = true) private String apiKey;
    @Setter @Getter @Accessors(chain = true) private IntegrationType integrationType;
    public  String getCommerceCode(){return commerceCode;}
    public  String getApiKey(){return apiKey;}
    public abstract String getHeaderCommerceCodeName();
    public abstract String getHeaderApiKeyName();
    public  void setIntegrationType(IntegrationType integrationType){this.integrationType = integrationType;}
    public  IntegrationType getIntegrationType(){ return integrationType;}

    public abstract Options buildOptions(Options options);
}
