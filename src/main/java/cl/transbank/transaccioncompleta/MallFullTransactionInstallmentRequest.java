package cl.transbank.transaccioncompleta;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionInstallmentRequest extends MallFullTransactionInstallmentsRequest {
    @SerializedName("commerce_code")private String commerceCode;
    @SerializedName("buy_order")private String buyOrder;
    @SerializedName("installments_number") private byte installmentsNumber;
}
