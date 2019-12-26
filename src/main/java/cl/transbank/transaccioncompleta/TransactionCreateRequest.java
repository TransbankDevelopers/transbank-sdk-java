package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
class TransactionCreateRequest extends WebpayApiRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("amount") private double amount;
    @SerializedName("card_number") private String cardNumber;
    @SerializedName("cvv") private short cvv;
    @SerializedName("card_expiration_date") private String cardExpirationDate;
    
}
