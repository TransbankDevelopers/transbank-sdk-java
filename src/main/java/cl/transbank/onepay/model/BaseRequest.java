package cl.transbank.onepay.model;

import lombok.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public abstract class BaseRequest {
    private String apiKey;
    private String appKey;
    private final boolean generateOttQrCode = true;
}
