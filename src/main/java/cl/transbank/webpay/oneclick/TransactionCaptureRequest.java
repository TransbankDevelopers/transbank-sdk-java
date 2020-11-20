package cl.transbank.webpay.oneclick;

import com.google.gson.annotations.SerializedName;

import cl.transbank.model.WebpayApiRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
class TransactionCaptureRequest extends WebpayApiRequest {
    @SerializedName("commerce_code") private String commerceCode;
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("capture_amount") private double amount;
}
