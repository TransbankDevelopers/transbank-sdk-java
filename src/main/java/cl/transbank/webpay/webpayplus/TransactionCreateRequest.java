package cl.transbank.webpay.webpayplus;

import cl.transbank.webpay.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class TransactionCreateRequest extends WebpayApiRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("amount") private double amount;
    @SerializedName("return_url") private String returnUrl;
}
