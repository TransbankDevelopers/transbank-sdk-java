package cl.transbank.webpay.oneclick;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class InscriptionDeleteRequest extends WebpayApiRequest {
    @SerializedName("username") private String username;
    @SerializedName("tbk_user") private String tbkUser;
}
