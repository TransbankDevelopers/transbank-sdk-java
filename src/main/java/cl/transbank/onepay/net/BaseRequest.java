package cl.transbank.onepay.net;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseRequest {
    private String apiKey;
    private String appKey;
}
