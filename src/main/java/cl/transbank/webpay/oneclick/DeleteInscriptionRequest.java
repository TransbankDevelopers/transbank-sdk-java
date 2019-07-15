package cl.transbank.webpay.oneclick;

import cl.transbank.webpay.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class DeleteInscriptionRequest extends WebpayApiRequest {
    @SerializedName("username") private String username;
    @SerializedName("tbk_user") private String tbkUser;
}
