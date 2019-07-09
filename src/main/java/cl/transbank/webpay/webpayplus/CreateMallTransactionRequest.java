package cl.transbank.webpay.webpayplus;

import cl.transbank.webpay.webpayplus.model.CreateMallTransactionDetails;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class CreateMallTransactionRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("return_url") private String returnUrl;
    @SerializedName("details") private List<CreateMallTransactionDetails.Detail> details;
}
