package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class MallFullTransactionRefundRequest extends WebpayApiRequest {
    @SerializedName("amount") private double amount;
    @SerializedName("commerce_code") private String commerceCode;
    @SerializedName("buy_order") private String buyOrder;
}
