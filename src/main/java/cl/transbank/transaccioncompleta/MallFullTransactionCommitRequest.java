package cl.transbank.transaccioncompleta;

import cl.transbank.model.WebpayApiRequest;
import cl.transbank.transaccioncompleta.model.MallTransactionCommitDetails;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class MallFullTransactionCommitRequest extends WebpayApiRequest {
    @SerializedName("details") private List<MallTransactionCommitDetails.Detail> details;

}
