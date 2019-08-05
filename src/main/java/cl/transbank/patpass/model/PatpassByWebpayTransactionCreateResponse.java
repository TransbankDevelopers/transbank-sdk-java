package cl.transbank.patpass.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class PatpassByWebpayTransactionCreateResponse {
    private String token;
    private String url;
}
