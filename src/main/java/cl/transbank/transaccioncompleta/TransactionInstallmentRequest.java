package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionInstallmentRequest extends TransactionInstallmentsRequest {
    @SerializedName("installments_number") private byte installmentsNumber;
}
