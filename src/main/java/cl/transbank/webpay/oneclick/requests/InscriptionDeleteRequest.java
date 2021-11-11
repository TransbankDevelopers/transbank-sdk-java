package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class InscriptionDeleteRequest extends WebpayApiRequest {
    private String username;
    private String tbkUser;
}
