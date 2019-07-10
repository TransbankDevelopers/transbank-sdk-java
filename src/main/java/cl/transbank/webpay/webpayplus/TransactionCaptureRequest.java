package cl.transbank.webpay.webpayplus;

import cl.transbank.webpay.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class TransactionCaptureRequest extends WebpayApiRequest {
    @SerializedName("commerce_code") private String commerceCode;
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("capture_amount") private double captureAmount;
}
