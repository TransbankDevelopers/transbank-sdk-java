package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@AllArgsConstructor
@Getter @Setter @ToString
public class TransactionCaptureRequest extends WebpayApiRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("capture_amount") private double captureAmount;
}
