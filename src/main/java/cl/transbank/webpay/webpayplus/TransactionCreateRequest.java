package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class TransactionCreateRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("amount") private double amount;
    @SerializedName("return_url") private String returnUrl;
}
