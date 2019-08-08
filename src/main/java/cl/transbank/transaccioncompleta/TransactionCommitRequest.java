package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionCommitRequest extends WebpayApiRequest {
    @SerializedName("token") private String token;
    @SerializedName("id_query_installments") private byte idQueryInstallments;
    @SerializedName("deferred_period_index") private byte deferredPeriodIndex;
    @SerializedName("grace_period") private Boolean gracePeriod;
}
