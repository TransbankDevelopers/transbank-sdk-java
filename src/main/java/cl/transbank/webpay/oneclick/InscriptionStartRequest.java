package cl.transbank.webpay.oneclick;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class InscriptionStartRequest extends WebpayApiRequest {
    @SerializedName("username") private String username;
    @SerializedName("email") private String email;
    @SerializedName("response_url") private String responseUrl;
}
