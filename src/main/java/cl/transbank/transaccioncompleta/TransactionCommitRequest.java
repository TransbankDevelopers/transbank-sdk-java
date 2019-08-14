package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCommitRequest extends WebpayApiRequest {
    @SerializedName("id_query_installments")  private long idQueryInstallments;
    @SerializedName("deferred_period_index") private byte deferredPeriodIndex;
    @SerializedName("grace_period") private boolean gracePeriod;
}
