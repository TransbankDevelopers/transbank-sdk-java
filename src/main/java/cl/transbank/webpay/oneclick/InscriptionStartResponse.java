package cl.transbank.webpay.oneclick;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class InscriptionStartResponse {
    @SerializedName("token") private String token;
    @SerializedName("url_webpay") private String urlWebpay;
    @SerializedName("error_message") private String errorMessage;
}
