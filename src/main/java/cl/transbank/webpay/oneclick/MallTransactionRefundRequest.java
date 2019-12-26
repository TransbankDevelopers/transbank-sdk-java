package cl.transbank.webpay.oneclick;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
class MallTransactionRefundRequest extends WebpayApiRequest {
    @SerializedName("commerce_code") private String commerceCode;
    @SerializedName("detail_buy_order") private String detail_buy_order;
    @SerializedName("amount") private double amount;
}
