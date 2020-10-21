package cl.transbank.webpay.webpayplus;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class TransactionRefundRequest extends WebpayApiRequest {
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("commerce_code") private String commerceCode;
    @SerializedName("amount") private double amount;

    public TransactionRefundRequest(double amount) {
        super();
        this.amount = amount;
    }
}
