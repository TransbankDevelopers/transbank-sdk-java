package cl.transbank.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Option {
    public abstract String getCommerceCode();
    public abstract String getApiKey();
    public abstract String getHeaderCommerceCodeName();
    public abstract String getHeaderApiKeyName();

}
