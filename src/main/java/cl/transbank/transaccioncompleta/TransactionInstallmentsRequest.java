package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionInstallmentsRequest extends WebpayApiRequest {
    @SerializedName("installments_number") private byte installmentsNumber;
}
