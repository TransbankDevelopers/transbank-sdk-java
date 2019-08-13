package cl.transbank.transaccioncompleta;

import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallTransactionCreateRequest extends WebpayApiRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("card_number") private String cardNumber;
    @SerializedName("card_expiration_date") private String cardExpirationDate;
    @SerializedName("details") private List<MallTransactionCreateDetails.Detail> details;
}
