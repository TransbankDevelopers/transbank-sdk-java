package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCommitRequest extends WebpayApiRequest {
    @SerializedName("id_query_installments")  private Long idQueryInstallments;
    @SerializedName("deferred_period_index") private Byte deferredPeriodIndex;
    @SerializedName("grace_period") private Boolean gracePeriod;
}
