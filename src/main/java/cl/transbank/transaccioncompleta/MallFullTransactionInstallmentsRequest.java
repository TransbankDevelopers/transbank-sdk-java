package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionInstallmentsRequest extends WebpayApiRequest {
    @SerializedName("commerce_code")private String commerceCode;
    @SerializedName("buy_order")private String buyOrder;
    @SerializedName("installments_number") private byte installmentsNumber;
}
