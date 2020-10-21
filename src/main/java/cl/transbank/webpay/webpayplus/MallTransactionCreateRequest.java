package cl.transbank.webpay.webpayplus;

import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class MallTransactionCreateRequest extends WebpayApiRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("return_url") private String returnUrl;
    @SerializedName("details") private List<MallTransactionCreateDetails.Detail> details;
}
