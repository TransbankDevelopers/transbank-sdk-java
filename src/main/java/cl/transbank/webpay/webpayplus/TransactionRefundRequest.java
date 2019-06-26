package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class TransactionRefundRequest {
    @SerializedName("amount") private double amount;
}
