package cl.transbank.webpay.oneclick.responses;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class InscriptionStartResponse {
    private String token;
    private String urlWebpay;
    private String errorMessage;
}
