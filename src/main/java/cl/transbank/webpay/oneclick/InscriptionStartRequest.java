package cl.transbank.webpay.oneclick;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class InscriptionStartRequest {
    @SerializedName("username") private String username;
    @SerializedName("email") private String email;
    @SerializedName("response_url") private String responseUrl;
}
