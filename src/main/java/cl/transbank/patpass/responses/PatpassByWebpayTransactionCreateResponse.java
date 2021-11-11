package cl.transbank.patpass.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class PatpassByWebpayTransactionCreateResponse {
    private String token;
    private String url;
}
