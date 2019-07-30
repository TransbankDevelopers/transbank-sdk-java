package cl.transbank.webpay.oneclick;

import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.oneclick.model.CreateMallTransactionDetails;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
class AuthorizeTransactionRequest extends WebpayApiRequest {
    @SerializedName("username") private String username;
    @SerializedName("tbk_user") private String tbkUser;
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("details") private List<CreateMallTransactionDetails.Detail> details;
}
